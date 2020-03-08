package com.team1.animalproject.view;

public class testapp {

	public static void main(String[] args) {

		int A[] = {3, 5, 6, 3, 3, 5};

	}

	public static int solution(int A[]){
		int sol = 0;
		for(int i = 0; i< A.length; i++){
			for(int k = 1; k <A.length; k++){
				if(k > i && A[k] == A[i]){
					sol ++;
				}
			}
		}
		return sol;
	}
}
