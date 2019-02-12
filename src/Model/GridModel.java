package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * class that holds the matrix that represents the game grid
 */
public class GridModel {

    private static List<List<Integer>> matrix;

    /**
     * static method for getting the matrix
     * will read from file at the beginning of every game
     * @param onGameStart true or false depending at which point in the game the method was called
     * @return matrix
     */
    public static List<List<Integer>> getMatrix(boolean onGameStart) {
        if (matrix == null || onGameStart) {
            String filepath = "/grid.txt";
            matrix = new ArrayList<>();

            InputStream input = GridModel.class.getResourceAsStream(filepath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
                Stream<String> stream = br.lines();
                stream.forEach(line -> {
                    List<String> elems = Arrays.asList(line.split(" "));
                    List<Integer> intElems = elems.stream().map(Integer::parseInt).collect(Collectors.toList());
                    matrix.add(intElems);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return matrix;
    }
}
