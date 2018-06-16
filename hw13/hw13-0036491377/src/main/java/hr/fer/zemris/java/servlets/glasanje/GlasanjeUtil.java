package hr.fer.zemris.java.servlets.glasanje;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is a utility class for loading, saving and updating the bands and their respective files.
 *
 * @author matej
 */
public class GlasanjeUtil {

    /**
     * Returns a list of bands loaded from given file in a sorted order based on their ids. All bands will have an
     * initial vote count of zero.
     *
     * @param fileName name of file to load from
     *
     * @return sorted list of bands
     *
     * @throws IOException if io exception occurs
     */
    public static Map<Integer, Band> loadBands(String fileName) throws IOException {
        String[] contents;
        contents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8).split("\n");

        Map<Integer, Band> bandList = new TreeMap<>();
        for (String line : contents) {
            String[] data = line.split("\t");
            int id = Integer.parseInt(data[0]);
            bandList.put(id, new Band(id, data[1], data[2]));
        }

        return bandList;
    }

    /**
     * Returns a list of bands with their vote counts updated. The list is sorted according to the votes.
     *
     * @param definitionFileName name of band definition file
     * @param resultFileName name of results file
     *
     * @return list of bands with their vote counts updated
     *
     * @throws IOException if io exception occurs
     */
    public static List<Band> getListOfBands(String definitionFileName, String resultFileName) throws IOException {
        Map<Integer, Band> bandMap = GlasanjeUtil.loadBands(definitionFileName);
        GlasanjeUtil.updateBandResults(resultFileName, bandMap);

        List<Band> resultList = new ArrayList<>(bandMap.values());
        resultList.sort((b1, b2) -> Integer.compare(b2.getVoteCount(), b1.getVoteCount()));
        return resultList;
    }

    /**
     * Updates given band map's vote counts according to the votes in the given file.
     *
     * @param resultFileName name of file with band results
     * @param bandMap band map
     *
     * @throws IOException if io exception occurs
     */
    public static void updateBandResults(String resultFileName, Map<Integer, Band> bandMap) throws IOException {
        Path path = Paths.get(resultFileName);
        if (!Files.exists(path)) {
            saveBandResults(resultFileName, bandMap);
        }

        BufferedReader br = new BufferedReader(new FileReader(resultFileName));

        String line = br.readLine();
        while (line != null) {
            String[] data = line.split("\t");
            int bandId = Integer.parseInt(data[0].trim());
            int votes = Integer.parseInt(data[1].trim());
            Band b = bandMap.get(bandId);
            b.setVoteCount(votes);
            line = br.readLine();
        }
        
        br.close();
    }

    /**
     * Saves given band map's results to the given file.
     *
     * @param fileName file where the results should be stored
     * @param bandMap map of bands
     *
     * @throws IOException if io exception occurs
     */
    public static void saveBandResults(String fileName, Map<Integer, Band> bandMap) throws IOException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        FileWriter fw = new FileWriter(fileName);

        for (Map.Entry<Integer, Band> e : bandMap.entrySet()) {
            Band b = e.getValue();
            fw.write(b.getId() + "\t" + b.getVoteCount() + "\r\n");
        }

        fw.flush();
        fw.close();
    }
}
