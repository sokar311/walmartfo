frontoffice-up:
	docker run --network host --name walmart_fo -p 8080:8080 -d sokar311/wallmartfo

frontoffice-image-up:
	docker build -t sokar311/wallmartfo .

frontoffice-init:
	./mvnw package -DskipTests
	make frontoffice-image-up
	make frontoffice-up

frontoffice-start:
	docker start walmart_fo

frontoffice-stop:
	docker stop walmart_fo

frontoffice-rm-container:
	docker rm walmart_fo

frontoffice-stop-rm:
	make frontoffice-stop
	make frontoffice-rm-container

frontoffice-rm-image:
	docker rmi sokar311/walmartfo

frontoffice-reset:
	make frontoffice-stop-rm
	make frontoffice-rm-image
	frontoffice-init

frontoffice-uninstall:
	make frontoffice-stop-rm
	make frontoffice-rm-image

frontoffice-run-test:
	./mvnw test
	