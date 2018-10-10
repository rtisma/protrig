version=latest
docker pull rtisma/protrig:${version}
docker run --rm -it rtisma/protrig:${version}  --spring.profiles.active=dev
