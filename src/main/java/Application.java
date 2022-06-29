import items.Const;

import java.util.Comparator;
import java.util.TreeMap;

public class Application {
    private final TreeMap<Integer, Integer> bids;
    private final TreeMap<Integer, Integer> asks;
    private final StringBuilder output;

    public Application() {
        bids = new TreeMap<>(Comparator.reverseOrder());
        asks = new TreeMap<>();
        output = new StringBuilder();
    }

    public String getOutput() {
        if (output.length() != 0)
            output.deleteCharAt(output.length() - 1);
        return output.toString();
    }

    public void execute(String command) {
        String[] splitCommand = command.split(",");
        String currentCommand = splitCommand[0];
        switch (currentCommand) {
            case Const.UPDATE:
                update(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]), splitCommand[3]);
                break;
            case Const.ORDER:
                order(Integer.parseInt(splitCommand[2]), splitCommand[1]);
                break;
            case Const.QUERY:
                query(splitCommand);
                break;
        }
    }

    public void update(int price, int size, String type) {
        if (isInRange(price, size)) {
            if (size == 0) {
                bids.remove(price);
                asks.remove(price);
            } else {
                if (type.equals(Const.BID))
                    bids.put(price, size);
                else
                    asks.put(price, size);
            }
        }
    }

    public void query(String[] splitCommands) {
        String command = splitCommands[1];
        int best;
        switch (command) {
            case Const.BEST_BID:
                best = bids.firstKey();
                output.append(best).append(",").append(bids.get(best)).append("\n");
                break;
            case Const.BEST_ASK:
                best = asks.firstKey();
                output.append(best).append(",").append(asks.get(best)).append("\n");
                break;
            case Const.SIZE:
                output.append(findByPrice(Integer.parseInt(splitCommands[2]))).append("\n");

        }
    }

    private void order(int size, String type) {
        int difference;
        int valid;
        TreeMap<Integer, Integer> reference = type.equals(Const.SELL) ? bids : asks;
        while (size != 0) {
            int bestPrice = reference.firstKey();
            int bestSize = reference.get(bestPrice);
            difference = bestSize - size;
            if (difference == 0) {
                reference.remove(bestPrice);
                break;
            } else if (difference > 0) {
                reference.put(bestPrice, difference);
                break;
            } else {
                valid = size + difference;
                reference.remove(bestPrice);
                size -= valid;
            }
        }
    }

    private boolean isInRange(int price, int size) {
        return (price >= 1 && price <= Math.pow(10, 9))
                && (size >= 0 && size <= Math.pow(10, 8));
    }

    private int findByPrice(int price) {
        if (asks.containsKey(price))
            return asks.get(price);
        if (bids.containsKey(price))
            return bids.get(price);
        return 0;
    }


    public static void main(String[] args) {
        FileHandler fh = new FileHandler();
        fh.run();
    }
}