package com.example.solution.service;

import com.example.solution.repository.ICitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
/** Implementation class of IFindConnectionBetweenCities
 * @author Durga Nihar Muddhuchetty
 * @author nihar.1882@gmail.com
 */
public class FindConnectionBetweenCitiesImpl  implements  IFindConnectionBetweenCities{

    @Autowired
    ICitiesRepository repository;

    Map<String,String> cityAndClusterMap = new HashMap<>();
    Map<String,Set<String>> clusterAndCityMap = new HashMap();
    List<String[]> connectionsList = null;



    @Override
    /**
     *  Finds if the two cities passed as input are connected ot not
     *
     * @param sourceCity : source city
     * @param destinationCity : desination sity
     *
     * @return 'yes' if the cities are connected , 'no' otherwise
     */
    public String findIfCitiesAreConnected(String sourceCity, String destinationCity) {

        sourceCity = sourceCity.trim();
        destinationCity = destinationCity.trim();

        connectionsList=repository.getCitiesData();
        cityAndClusterMap.clear();
        clusterAndCityMap.clear();

        for(int i=0;i<connectionsList.size();i++){
                fillUpMaps(connectionsList.get(i),i);
          }


        if(cityAndClusterMap.get(sourceCity)==null || cityAndClusterMap.get(destinationCity)==null){
            return "no";
        }

        return cityAndClusterMap.get(sourceCity).equals(cityAndClusterMap.get(destinationCity))?"yes":"no";
    }

    /**
     *  Fills in the data structures (cityAndClusterMap,clusterAndCityMap) to find if the cities are connected
     *
     * @param cities array of two cities
     * @param index  index number which is appended to the cluster name that would be assigned to the cities
     */
    public void fillUpMaps(String[] cities,int index){

        Set<String> keyInputSet = cityAndClusterMap.keySet();
        String sourceCity = cities[0].trim();
        String destinationCity = cities[1].trim();

        if(!keyInputSet.contains(sourceCity) && !keyInputSet.contains(destinationCity) ){
            String clusterIndex = "cluster"+index;
            cityAndClusterMap.put(sourceCity,clusterIndex);
            cityAndClusterMap.put(destinationCity,clusterIndex);

            clusterAndCityMap.put(clusterIndex,new HashSet<>());
            clusterAndCityMap.get(clusterIndex).add(sourceCity);
            clusterAndCityMap.get(clusterIndex).add(destinationCity);
        }else if(keyInputSet.contains(sourceCity) && keyInputSet.contains(destinationCity)  ){
            String sourceCityClusterIndex = cityAndClusterMap.get(sourceCity);
            String destinationCityClusterIndex = cityAndClusterMap.get(destinationCity);

            // remove cities from destinationCityClusterIndex of clusterAndCityMap and add them to sourceCityClusterIndex
            Set<String> citiesInDestinationCluster = clusterAndCityMap.get(destinationCityClusterIndex);
            if(!destinationCityClusterIndex.equals(sourceCityClusterIndex)) {
                clusterAndCityMap.remove(destinationCityClusterIndex);
            }
            citiesInDestinationCluster.stream().forEach(x->{
                 cityAndClusterMap.put(x,sourceCityClusterIndex);
            });

            //add current source city , destination city to the sourceCityClusterIndex
            clusterAndCityMap.get(sourceCityClusterIndex).add(sourceCity);
            clusterAndCityMap.get(sourceCityClusterIndex).add(destinationCity);

            //add cities obtained from destinationCityClusterIndex to sourceCityClusterIndex
            clusterAndCityMap.get(sourceCityClusterIndex).addAll(citiesInDestinationCluster);

            // Making source and destination cities belong to sourceCityClusterIndex
            cityAndClusterMap.put(sourceCity,sourceCityClusterIndex);
            cityAndClusterMap.put(destinationCity,sourceCityClusterIndex);
        }else if(keyInputSet.contains(sourceCity) && !keyInputSet.contains(destinationCity) ){
            String sourceCityClusterIndex = cityAndClusterMap.get(sourceCity);

            //Assign sourceCityClusterIndex to destinationCity
            cityAndClusterMap.put(destinationCity,sourceCityClusterIndex);

            //Update clusterAndCityMap so that sourceCityClusterIndex contains destination city
            clusterAndCityMap.get(sourceCityClusterIndex).add(destinationCity);
        }else if(!keyInputSet.contains(sourceCity) && keyInputSet.contains(destinationCity)){
            String destinationCityClusterIndex = cityAndClusterMap.get(destinationCity);

            //Assign destinationCityClusterIndex to sourceCity
            cityAndClusterMap.put(sourceCity,destinationCityClusterIndex);

            //Update clusterAndCityMap so that destinationCityClusterIndex contains source city
            clusterAndCityMap.get(destinationCityClusterIndex).add(sourceCity);
        }


    }

    // Getters and setter used in unit tests

    public Map<String, String> getCityAndClusterMap() {
        return cityAndClusterMap;
    }

    public Map<String, Set<String>> getClusterAndCityMap() {
        return clusterAndCityMap;
    }

    public void setCityAndClusterMap(Map<String, String> cityAndClusterMap) {
        this.cityAndClusterMap = cityAndClusterMap;
    }

    public void setClusterAndCityMap(Map<String, Set<String>> clusterAndCityMap) {
        this.clusterAndCityMap = clusterAndCityMap;
    }
}
