### Install
- gradlew build -x test
- docker-compose up --build -d

#### Use
- Build image: 
curl --header "Content-Type: application/json" --request POST --data '{"title": "any title","image": "https://images.pexels.com/photos/60597/dahlia-red-blossom-bloom-60597.jpeg"}' http://localhost:30001/thumbnail
- Download image: 
wget http://localhost:30001/thumbnail.png
