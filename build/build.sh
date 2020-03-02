#!/bin/bash
set -e

if [[ $1 == "quiet" ]]; then
    MAVEN_FLAGS="-q"
    DOCKER_BUILD_FLAGS="-q"
fi

# 这个脚本应该运行在 tsf-demo-simple 目录下
cd "$(dirname ${BASH_SOURCE[0]})/.."

build_docker_image() {
    demo_dir=$1
    jar_file=$(find $demo_dir/*.jar -printf "%f")

    echo "Building docker image for $jar_file..."

    sed -e "s/:jar_file:/$jar_file/g" build/Dockerfile.template > $demo_dir/Dockerfile

    if [[ $jar_file =~ (.*)-([0-9.]+) ]]; then
        repo_and_tag=${BASH_REMATCH[1]}:${BASH_REMATCH[2]}
        docker build $demo_dir $DOCKER_BUILD_FLAGS -t $repo_and_tag
        docker save $repo_and_tag > $DOCKER_IMAGE_DEST_DIR/$repo_and_tag.tar
        echo "Built docker image saved at $DOCKER_IMAGE_DEST_DIR/$repo_and_tag.tar."
        echo ""
    else
        echo >&2 "Jar file not found or name pattern not matched. Abort."
        exit 1
    fi
}

echo "Building demo..."
command -v mvn >/dev/null 2>&1 || { echo >&2 "Maven is required to build demo. Abort."; exit 1; }
mvn --settings build/settings.xml $MAVEN_FLAGS clean package
echo "Building demo finished."
echo ""

command -v docker >/dev/null 2>&1 || { echo "Docker image is not built because docker is not installed."; exit 0; }

DOCKER_IMAGE_DEST_DIR=build/docker-images
rm -rf $DOCKER_IMAGE_DEST_DIR

PROVIDER_IMAGE_DEST_DIR=$DOCKER_IMAGE_DEST_DIR/provider-demo
CONSUMER_IMAGE_DEST_DIR=$DOCKER_IMAGE_DEST_DIR/consumer-demo

mkdir -p $DOCKER_IMAGE_DEST_DIR/{provider,consumer}-demo

cp provider-demo/target/*.jar $PROVIDER_IMAGE_DEST_DIR
cp consumer-demo/target/*.jar $CONSUMER_IMAGE_DEST_DIR

build_docker_image $PROVIDER_IMAGE_DEST_DIR
build_docker_image $CONSUMER_IMAGE_DEST_DIR
