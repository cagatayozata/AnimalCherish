package com.team1.animalproject.blockchain.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.IOException;

public class IpfsService {

	public static final String ipfsAddress = "/ip4/127.0.0.1/tcp/5001";

	public static Multihash save(String savedFile) throws IOException {
		IPFS ipfs = new IPFS(ipfsAddress);
		ipfs.refs.local();
		NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(savedFile));
		MerkleNode addResult = ipfs.add(file).get(0);
		return addResult.hash;
	}

	public static String getFile(String hash) throws IOException {
		IPFS ipfs = new IPFS(ipfsAddress);
		byte[] bytes = ipfs.get(Multihash.fromBase58(hash));
		return new String(bytes);
	}
}
