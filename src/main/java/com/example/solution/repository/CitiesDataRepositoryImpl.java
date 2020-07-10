package com.example.solution.repository;

import com.example.solution.Exception.CustomException;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
/** Implementation class of ICitiesRepository
 * @author Durga Nihar Muddhuchetty
 * @author nihar.1882@gmail.com
 */
public class CitiesDataRepositoryImpl implements ICitiesRepository {

    public static final String FILE_LOCATION="src/main/resources";
    public static final String FILE_NAME="city.txt";

    @Override
    /**
     *  Reads data from city.txt file
     * @return content of the file after splitting the data based on the delimiter : ','     *
     */
    public List<String[]> getCitiesData() {

        List<String[]> listOfConnections = new ArrayList<>();
        String line ="";

        try {
            //Read data from the file
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_LOCATION + "//" + FILE_NAME));
            while((line=bufferedReader.readLine())!=null){
                if(line.trim().isEmpty() || line.equals("")){
                    continue;
                }
                String[] citiesWithConnection = line.split(",");
                if(citiesWithConnection.length!=2){
                    throw new CustomException(" Data input file in not well formed .");
                }
                listOfConnections.add(citiesWithConnection);
            }
        }
        catch (IOException e){
            CustomException customException = new CustomException(e.getMessage());
          throw customException;
        }

        return listOfConnections;
    }
}
