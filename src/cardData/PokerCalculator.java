package cardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.math.BigInteger;

public class PokerCalculator {
	static String[] types = new String[] { "cb", "sb", "dr", "hr" };
	static final String[] cardTypes = new String[] { "A", "2", "3", "4", "5", "6", "7", "8", "9", "1", "J", "Q", "K" };
	static String[] combo = new String[] { "Royal Flush", "Straight Flush", "Quads", "Full House", "Flush", "Straight",
			"Trips", "Two Pair", "Pair", "High Card" };
	static int cardsLeft = 52;
	static HashMap<String, Integer> combos = new HashMap<String, Integer>();
	// Left Cards
	static HashMap<String, Integer> Clubs = new HashMap<String, Integer>();
	static HashMap<String, Integer> Spades = new HashMap<String, Integer>();
	static HashMap<String, Integer> Hearts = new HashMap<String, Integer>();
	static HashMap<String, Integer> Diamonds = new HashMap<String, Integer>();

	public static void main(String[] args) {
		/*
		 * Some way of getting the current hand as input In variable currenthand
		 */
		String currentHand = "5cb5hr5dr2sb9dr";// "5cb4cb3cb2cbAcb";//
												// "8cb8sb8hr3cb3dr";//"AsbJcb3dr9drAhr";//"Acb2cb8hr6sb3dr";

		ArrayList<String> allCards = new ArrayList<String>();
		for (String type : types) {
			for (String entry : cardTypes) {
				allCards.add(entry + type);
			}
		}

		// Fill the Cards and values into the hashmaps
		fillHash(Clubs);
		fillHash(Spades);
		fillHash(Hearts);
		fillHash(Diamonds);
		fillCombos();
		// update the cards left
		// cardsLeft -= currentHand.length() / 3;
		// remove the drawn cards from the possible cards(hashmaps)
		// removeDrawn(currentHand);
		String currentCombo = getCurrent(currentHand);
		System.out.println(currentCombo);
		getProbabilities(currentCombo);

	}

	public static String getCurrent(String currentHand) {
		// Flush check
		int Flush = 0;
		char color = currentHand.charAt(1);
		if (color == currentHand.charAt(4) && color == currentHand.charAt(7) && color == currentHand.charAt(10)
				&& color == currentHand.charAt(13)) {
			// Is flush Check if Straight Flush

			if (isStraight(currentHand, 1) == 1) {
				return "Straight Flush";
			} else if (isStraight(currentHand, 1) == 2) {
				return "Royal Flush";
			} else {
				Flush = 1;
			}
		}
		int sameCounted = sameCount(currentHand);
		if (sameCounted == 1) {
			return "Quads";
		} else if (sameCounted == 2) {
			return "Full House";
		} else if (Flush == 1) {
			return "Flush";
		} else if (sameCounted == 3) {
			return "Trips";
		} else if (sameCounted == 4) {
			return "Two Pair";
		} else if (sameCounted == 5) {
			return "Pair";
		} else {
			String highestCard = "";
			if (currentHand.contains("A")) {
				highestCard = "A";
			} else {
				highestCard += cardTypes[getIndex(currentHand).get(getIndex(currentHand).size() - 1)];
				System.out.println(highestCard);
			}
			return "High Card " + highestCard;
		}

	}

	public static int isStraight(String currentHand, int isFlush) {
		/*
		 * String types = ""; for(int i = 0; i < currentHand.length(); i = i + 3) {
		 * types += currentHand.charAt(i); } ArrayList<Integer> cardIndex = new
		 * ArrayList<Integer>(); String cardTypeString = String.join("", cardTypes);
		 * for(int i = 0; i < 5; i++) {
		 * if(!cardIndex.contains(cardTypeString.indexOf(types.charAt(i))));
		 * cardIndex.add(cardTypeString.indexOf(types.charAt(i))); }
		 * Collections.sort(cardIndex);
		 */
		ArrayList<Integer> cardIndex = getIndex(currentHand);
		if (cardIndex.size() == 5) {
			if (cardIndex.get(4) - cardIndex.get(0) == 4) {
				return 1;
			}
			if (cardIndex.get(0) == 0 && cardIndex.get(1) == 9 && isFlush == 1) {
				return 2;
			}
		}
		return 0;

	}

	public static int sameCount(String currentHand) {
		String types = "";
		for (int i = 0; i < currentHand.length(); i = i + 3) {
			types += currentHand.charAt(i);
		}
		// sort types string
		char[] typesArray = types.toCharArray();
		Arrays.sort(typesArray);
		types = String.valueOf(typesArray);
		// count occurrences
		String occurences = "";
		for (int j = 0; j < 5; j++) {
			occurences += (types.length() - types.replace(types.substring(j, j + 1), "").length());
			j += (types.length() - types.replace(types.substring(j, j + 1), "").length() - 1);
		}
		if (occurences.contains("4")) {
			// QuadsNumber
			return 1;
		}
		if (occurences.contains("3")) {
			if (occurences.contains("2")) {
				// Full House
				return 2;
			} else {
				// Trips
				return 3;
			}
		}
		if (occurences.contains("2")) {
			if (occurences.contains("1") && occurences.length() == 3) {
				// Two Pair
				return 4;
			} else {
				// Pair
				return 5;
			}
		}

		return 0;
	}

	public static ArrayList<Integer> getIndex(String currentHand) {
		String types = "";
		for (int i = 0; i < currentHand.length(); i = i + 3) {
			types += currentHand.charAt(i);
		}
		ArrayList<Integer> cardIndex = new ArrayList<Integer>();
		String cardTypeString = String.join("", cardTypes);
		for (int i = 0; i < 5; i++) {
			if (!cardIndex.contains(cardTypeString.indexOf(types.charAt(i))))
				;
			cardIndex.add(cardTypeString.indexOf(types.charAt(i)));
		}
		Collections.sort(cardIndex);
		return cardIndex;
	}

	public static void fillHash(HashMap<String, Integer> map) {
		for (String entry : cardTypes) {
			map.put(entry, 1);
		}
	}

	public static void removeDrawn(String currentHand) {
		for (int i = 0; i < currentHand.length(); i += 3) {
			switch (currentHand.charAt(i + 1)) {
			case 'c':
				Clubs.put(currentHand.substring(i, i + 1), 0);
				break;
			case 's':
				Spades.put(currentHand.substring(i, i + 1), 0);
				break;
			case 'h':
				Hearts.put(currentHand.substring(i, i + 1), 0);
				break;
			case 'd':
				Diamonds.put(currentHand.substring(i, i + 1), 0);
				break;
			}

		}
	}

	public static void fillCombos() {
		for (int i = 0; i < combo.length; i++) {
			combos.put(combo[i], i);
		}
	}

	public static void getProbabilities(String currentCombo) {
		//
		long cardProbability = nCr(cardsLeft, 5);
		for (int i = 0; i < 5; i++) {
			cardProbability = cardProbability * (1 / (cardsLeft - 1));
		}
		int comboValue = combos.get(currentCombo);
		int[] pairs = checkPairs(0);
		int[] trips = checkTrips();
		int fullHouseFreq = 0;
		if (comboValue == 0) {
			double royalFreq = checkRoyal(Clubs) + checkRoyal(Spades) + checkRoyal(Diamonds) + checkRoyal(Hearts);
			if (royalFreq > 0) {
				double royalProb = (cardProbability > 0) ? royalFreq / cardProbability : 0;
			} else {
				double royalProb = 0;
			}
		}
		if (comboValue >= 1) {
			int straightFlushFreq = checkStraightFlush(Clubs) + checkStraightFlush(Spades)
					+ checkStraightFlush(Diamonds) + checkStraightFlush(Hearts);
			double straightProb = (cardProbability > 0) ? straightFlushFreq / cardProbability : 0;
		}
		if (comboValue >= 2) {
			int quadFreq = checkQuads() * (checkRemaining(Clubs) + checkRemaining(Spades) + checkRemaining(Hearts)
					+ checkRemaining(Diamonds) - 1);
			double quadProb = (cardProbability > 0) ? quadFreq / cardProbability : 0;
		}
		if (comboValue >= 3) {
			int impossibleCases = (trips[0] - trips[1]) * 6 + trips[1] * 3;
			fullHouseFreq = checkTrips()[0] * pairs[0] - impossibleCases;
			double fullHouseProb = (cardProbability > 0) ? fullHouseFreq / cardProbability : 0;
			System.out.print(impossibleCases + "ic \n" );
		}
		if (comboValue >= 4) {
			// Flush
			int flushFreq = checkFlush(Clubs) + checkFlush(Spades) + checkFlush(Hearts) + checkFlush(Diamonds);
			double flushProb = (cardProbability > 0) ? flushFreq / cardProbability : 0;

		}
		if (comboValue >= 5) {
			// Straight
			int straightFreq = checkStraight();
			double straightProb = (cardProbability > 0) ? straightFreq / cardProbability : 0;
		}
		if (comboValue >= 6) {
			// Trips
			long missingCasesCompensation = nCr(cardsLeft - 3, 2) / trips[0] * trips[1];
			long tripsFreq = trips[0] * (nCr(cardsLeft - 4, 2)) + missingCasesCompensation - fullHouseFreq;
			System.out.print("trips" + tripsFreq + "\n");
			System.out.print(cardsLeft + "\n");
			System.out.println(trips[0] + " " + fullHouseFreq);
		}
		/*
		if (true) {// (comboValue >= 7) {
			// Two Pair
			long impossibleCases = pairs[1] * 6 + pairs[2] * 3 + pairs[3];
			long difColorPairs = checkPairs(1)[0] + checkPairs(2)[0];
			//long sameColorPairs = pairs
			System.out.println(difColorPairs);
			long twoPairFreq = (pairs[0] * pairs[0] - impossibleCases);
			System.out.print(twoPairFreq + "here" + impossibleCases + " " + pairs[0]);
		}
		*/

	}

	public static int checkRemaining(HashMap<String, Integer> map) {
		int remaining = 0;
		for (String key : cardTypes) {
			remaining += map.get(key);
		}
		return remaining;
	}

	public static int checkRoyal(HashMap<String, Integer> map) {
		String[] royal = new String[] { "A", "K", "Q", "J", "1" };
		for (String card : royal) {
			System.out.println(map.get("A"));
			if (map.get(card) == 0) {
				return 0;
			}
			// System.out.println(map.get(card).getClass().getName());

		}

		return 1;
	}

	public static int checkStraightFlush(HashMap<String, Integer> map) {
		int posibilities = 0;
		int counter = 0;
		ArrayList<Integer> counted = new ArrayList<Integer>();
		for (String key : cardTypes) {
			if (map.get(key) == 1) {
				counter += 1;
			} else {
				counted.add(counter);
				counter = 0;
			}
		}
		if (counter != 0) {
			counted.add(counter);
		}
		for (int number : counted) {
			if (number >= 5) {
				posibilities += number - 4;
			}
		}
		return posibilities;
	}

	public static int checkQuads() {
		int posibilities = 0;
		for (String key : cardTypes) {
			if (Spades.get(key) == 1 && Clubs.get(key) == 1 && Hearts.get(key) == 1 && Diamonds.get(key) == 1) {
				posibilities++;
			}
		}
		return posibilities;
	}

	public static int[] checkTrips() {
		int typeSum = 0;
		int posibilities = 0;
		int missingCases = 0;
		for (String key : cardTypes) {
			typeSum = Clubs.get(key) + Spades.get(key) + Hearts.get(key) + Diamonds.get(key);
			switch (typeSum) {
			case 4:
				posibilities += 4;
				break;
			case 3:
				posibilities += 1;
				missingCases += 1;
				break;
			}
		}
		int[] returnValue = new int[] { posibilities, missingCases };
		return returnValue;
	}

	public static int[] checkPairs(int whichPairs) {
		//which pairs 0 means all pairs 1 only red 2 only black
		int posibilities = 0;
		int typeSum = 0;
		int comboMinus = 0;
		int[] cases = new int[4];
		cases[1] = 0;
		cases[2] = 0;
		cases[3] = 0;
		for (String key : cardTypes) {
			switch(whichPairs) {
			case 0: typeSum = Clubs.get(key) + Spades.get(key) + Hearts.get(key) + Diamonds.get(key);
			break;
			case 1: typeSum = Hearts.get(key) + Diamonds.get(key);
			break;
			case 2: typeSum = Spades.get(key) + Clubs.get(key);
			break;
			}
			switch (typeSum) {
			case 4:
				posibilities += 6;
				cases[1] += 6;
				break;
			case 3:
				posibilities += 3;
				cases[2] += 3;
				break;
			case 2:
				posibilities += 1;
				cases[3] += 1;
				break;
			}
		}
		cases[0] = posibilities;
		return cases;
	}

	public static int checkFlush(HashMap<String, Integer> map) {
		int posibilities = 0;
		int remaining = checkRemaining(map);
		if (remaining < 5) {
			return 0;
		}
		switch (remaining) {
		case 13:
			posibilities += 1287;
			break;
		case 12:
			posibilities += 792;
			break;
		case 11:
			posibilities += 462;
			break;
		case 10:
			posibilities += 252;
			break;
		case 9:
			posibilities += 126;
			break;
		case 8:
			posibilities += 56;
			break;
		case 7:
			posibilities += 21;
			break;
		case 6:
			posibilities += 6;
			break;
		case 5:
			posibilities += 1;
			break;
		}
		return posibilities;
	}

	public static int checkStraight() {
		int[] cardAmounts = new int[13];
		int counter = 0;
		int posibilities = 0;

		for (String key : cardTypes) {
			cardAmounts[counter] = Clubs.get(key) + Spades.get(key) + Hearts.get(key) + Diamonds.get(key);
			counter++;
		}
		for (int i = 0; i < 9; i++) {
			posibilities += cardAmounts[i] * cardAmounts[i + 1] * cardAmounts[i + 2] * cardAmounts[i + 3]
					* cardAmounts[i + 4];
		}
		posibilities += cardAmounts[0] * cardAmounts[12] * cardAmounts[11] * cardAmounts[10] * cardAmounts[9];
		return posibilities;
	}

	public static BigInteger factorial(BigInteger number) {
		BigInteger bigOne = new BigInteger("1");
		if (number.equals(bigOne)) {
			return bigOne;
		} else
			return number.multiply(factorial(number.subtract(bigOne)));
	}

	public static long nCr(int number1, int number2) {
		BigInteger bigNumber1 = new BigInteger(String.valueOf(number1));
		BigInteger bigNumber2 = new BigInteger(String.valueOf(number2));
		long solution = (factorial(bigNumber1)
				.divide(factorial(bigNumber2).multiply(factorial(bigNumber1.subtract(bigNumber2))))).longValueExact();
		return solution;
	}
}