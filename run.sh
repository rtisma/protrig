version=latest
docker pull rtisma1/protrig:${version}
docker run --rm -it rtisma1/protrig:${version}  --spring.profiles.active=dev
