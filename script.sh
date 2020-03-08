#!/bin/sh
# AnimalCherish
# Cagatay Ozata

echo ""
echo "= = ="
echo ""
echo "Animal Cherish Deploy Script"
echo ""
echo "= = =" 
echo ""
echo "Bu scriptin calisabilmesi icin bilgisayarinizda Docker kurulu ve calisir durumda olmasi gerekmektedir."
echo "Ayrica bilgisayarinizin aktif bir internet baglantisina sahip olmasi gerekmektedir."
echo ""
echo "= = ="
echo ""

while true; do
    read -p "Devam etmek istiyor musunuz? [Y, N]: " yn
    case $yn in
        [Yy]* ) 
            echo "[INFO - AnimalCherish] Docker Build Operation is STARTED!"
            echo ""
            docker build -f Dockerfile -t docker-spring-boot .
            echo ""
            echo "[INFO - AnimalCherish] Docker Build Operation is FINISHED!"
            echo ""

            echo "[INFO - AnimalCherish] Saving Docker Image Saving Operation is STARTED!"
            echo ""
            docker save -o docker-spring-boot.tar docker-spring-boot
            echo ""
            echo "[INFO - AnimalCherish] Saving Docker Image Saving Operation is FINISHED!"
            echo ""

            echo "[INFO - AnimalCherish] Sending Docker Image to Server Operation is STARTED!"
            echo ""
            echo "Guncel sifreyi bilmiyorsaniz asagidaki sayfadan kontrol edebilirsiniz."
            echo "https://github.com/cagatayozata/AnimalCherish/wiki/Deploy-User"
            echo ""
            scp docker-spring-boot.tar deployuser@138.68.67.165:~
            echo ""
            echo "[INFO - AnimalCherish] Sending Docker Image to Server Operation is FINISHED!"
            echo ""

            echo "[INFO - AnimalCherish] Deleting Saved Docker File Operation is STARTED!"
            echo ""
            rm docker-spring-boot.tar
            echo ""
            echo "[INFO - AnimalCherish] Deleting Saved Docker File Operation is FINISHED!"
            echo ""

            echo "[INFO - AnimalCherish] Connecting to Server Operation is STARTED!"
            echo ""
            echo "!!!"
            echo "Sifrenizi girdikten sonra acilan sunucu ekraninda asagidaki komutu calistirip islem tamamlandiktan sonra exit ile cikabilirsiniz."
            echo ""
            echo "./script.sh"
            echo ""
            echo "!!!"
            echo ""
            ssh deployuser@138.68.67.165
            echo ""
            echo "[INFO - AnimalCherish] Connecting to Server Operation is FINISHED!"
            echo ""

            break;;
        [Nn]* ) 
            exit;;
        * ) echo "Lutfen gecerli deger giriniz. [Y/N]";;
    esac
done