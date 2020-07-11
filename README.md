# account-service
Account Service

docker network create -d bridge account 
docker network ls  

docker build -t gar2000b/account-service .  
docker run -it -d -p 9087:9087 --network="account" --name account-service gar2000b/account-service  

All optional:

docker create -it gar2000b/account-service bash  
docker ps -a  
docker start ####  
docker ps  
docker attach ####  
docker remove ####  
docker image rm gar2000b/account-service  
docker exec -it account-service sh  
docker login  
docker push gar2000b/account-service  