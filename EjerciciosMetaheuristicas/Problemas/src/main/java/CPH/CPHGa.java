package CPH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CPHGa {
	private CPHInstance instance;

	public CPHGa(CPHInstance instance) {
		this.instance = instance;
	}

	public void ga(CPHInstance instance) {
		CPHRandomConstructive rc = new CPHRandomConstructive(instance);
		List<CPHSolution> town = rc.solutions(50);

		CPHSolution solution = crossing(town.get(0), town.get(1));
		
		System.out.println(Arrays.toString(solution.getHubs().toArray()));
		System.out.println(Arrays.toString(solution.getSpokes()));

	}

	public CPHSolution crossing(CPHSolution f1, CPHSolution f2) {
		int numSpokes = instance.getnNodes();
		int numHubs = instance.getnHubs();
		int halfHubs = numHubs / 2;
		int halfSpokes = numSpokes / 2;

		List<CPHNode> hubs = new ArrayList<>();
		int[] spokes = new int[numSpokes];

		// Alternate hubs
		for (int i = 0; i < halfHubs; i++) {
			hubs.add(f1.getHubs().get(i));
		}

		for (int i = halfHubs; i < numHubs; i++) {
			if (!hubs.contains(f2.getHubs().get(i))) {
				hubs.add(f2.getHubs().get(i));
			} else {
				hubs.add(f1.getHubs().get(i));
			}
		}

		// Alternate spokes
		for (int i = 0; i < halfSpokes; i++) {
			spokes[i] = f1.getSpokes()[i];
		}

		for (int i = halfSpokes; i < numSpokes; i++) {
			spokes[i] = f2.getSpokes()[i];
		}

		// Repair spokes
		Random r = new Random();
		Map<Integer, Integer> positionHubsSon = new HashMap<>();
		Map<Integer, Integer> positionHubsf1 = new HashMap<>();
		Map<Integer, Integer> positionHubsf2 = new HashMap<>();

		for (int i = 0; i < numHubs; i++) {
			positionHubsf1.put(f1.getHubs().get(i).getIndex(), i);
			positionHubsf2.put(f2.getHubs().get(i).getIndex(), i);
			positionHubsSon.put(i, hubs.get(i).getIndex());
		}

		for (int i = 0; i < numSpokes; i++) {
			if (spokes[i] == -1) {
				spokes[i] = hubs.get(r.nextInt(hubs.size())).getIndex();
			} else if (positionHubsSon.containsValue(i)) {
				spokes[i] = -1;
			} else if (!positionHubsSon.containsValue(spokes[i])) {
				if (positionHubsf1.containsKey(spokes[i])
						|| (positionHubsf1.containsKey(spokes[i]) && positionHubsf2.containsKey(spokes[i]))) {
					spokes[i] = positionHubsSon.get(positionHubsf1.get(spokes[i]));
				} else if (positionHubsf2.containsKey(spokes[i])) {
					spokes[i] = positionHubsSon.get(positionHubsf2.get(spokes[i]));
				}
			}
		}

		return new CPHSolution(instance, hubs, spokes);
	}

	public CPHSolution mutation(CPHSolution solution) {
		Random r = new Random();
		int positionOne;
		int positionTwo;
		
		List<CPHNode> hubs = new ArrayList<>(solution.getHubs());
		int[] spokes = solution.getSpokes().clone();
		
		// Mutation hub
		positionOne = r.nextInt(hubs.size());
		positionTwo = r.nextInt(hubs.size());
		
		CPHNode nodeOne = hubs.get(positionOne);
		CPHNode nodeTwo = hubs.get(positionTwo);
		
		hubs.set(positionOne, nodeTwo);
		hubs.set(positionTwo, nodeOne);
		
		// Mutation spoke
		positionOne = r.nextInt(hubs.size());
		positionTwo = r.nextInt(hubs.size());
		
		int spokeOne = spokes[positionOne];
		int spokeTwo = spokes[positionTwo];
		
		spokes[positionOne] = spokeTwo;
		spokes[positionTwo] = spokeOne;
		
		return new CPHSolution(instance, hubs, spokes);
	}
}
