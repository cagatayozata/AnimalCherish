package com.team1.animalproject.service;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class BlockchainService implements IBaseService<Block> {

    private List<String> hashes;

    @Autowired
    private AnimalService animalService;

    public void kullaniciDosyasiOlustur(String userId) throws IOException {
        String json = null;
            File newFile = new File(userId+".achain");
            if(!newFile.exists()) {
                newFile.createNewFile();
            }
    }

    public void dosyayiGuncelHaleGetir(String userId) throws IOException {

        String json = null;
        File newFile = new File(userId+".achain");

        hashes = new ArrayList<>();

        List<Animal> all = animalService.getAll();
        if(all != null) {
            List<Block> blocks = new ArrayList<>();
            all.stream().forEach(animal -> {
                blocks.add(Block.builder()
                        .id(animal.getId())
                        .birthdate(animal.getBirthdate())
                        .name(animal.getName())
                        .address(animal.getAddress())
                        .cinsId(animal.getCinsId())
                        .turId(animal.getTurId())
                        .build());
            });

            blocks.stream().forEach(block -> {
                hashes.add(block.hashCode()+"");
            });

            FileWriter writer = new FileWriter(newFile);
            for(String str: hashes) {
                writer.write(str + "\n");
            }
            writer.close();
        }

    }

    public boolean validate(String userId) {
        hashes = new ArrayList<>();

        List<Animal> all = animalService.getAll();
        if (all != null) {
            List<Block> blocks = new ArrayList<>();
            all.stream().forEach(animal -> {
                blocks.add(Block.builder()
                        .id(animal.getId())
                        .birthdate(animal.getBirthdate())
                        .name(animal.getName())
                        .address(animal.getAddress())
                        .cinsId(animal.getCinsId())
                        .turId(animal.getTurId())
                        .build());
            });

            blocks.stream().forEach(block -> {
                hashes.add(block.hashCode() + "");
            });

            List<String> hashUser = readFile(userId);
            if(hashes.size() == hashUser.size() && !hashes.equals(hashUser)){
                return false;
            }
        }
        return true;
    }


    public List<String> readFile(String userId) {
        List<String> hashlist = new ArrayList<>();
        try {
            File myObj = new File(userId + ".achain");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                hashlist.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return hashlist;
    }

    @Override
    public List<Block> getAll() {
        return null;
    }

    @Override
    public void save(Block block) {

    }

    @Override
    public void update(Block block) {

    }

    @Override
    public void delete(List<Block> t) {

    }
}
