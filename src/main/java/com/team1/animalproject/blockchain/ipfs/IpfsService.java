package com.team1.animalproject.blockchain.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.IOException;

public class IpfsService {

	public static Multihash save(String savedFile) throws IOException {
		IPFS ipfs = new IPFS("/dnsaddr/ipfs.infura.io/tcp/5001/https");
		NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(savedFile));
		MerkleNode addResult = ipfs.add(file).get(0);
		return addResult.hash;
	}

	public static String getFile(String hash) throws IOException {
		long start = System.currentTimeMillis();
		IPFS ipfs = new IPFS("/dnsaddr/ipfs.infura.io/tcp/5001/https");
		byte[] bytes = ipfs.get(Multihash.fromBase58(hash));
		System.out.println("IPFS Toplam Gecen Sure: " + ((System.currentTimeMillis() - start) / 1000F));
		return new String(bytes);
	}
}
