version=latest
port=8888
docker pull rtisma1/protrig:${version}
docker run --rm -p ${port}:8080 -it rtisma1/protrig:${version}  --spring.profiles.active=dev
