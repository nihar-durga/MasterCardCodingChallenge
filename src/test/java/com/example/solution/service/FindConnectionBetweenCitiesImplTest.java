package com.example.solution.service;

import com.example.solution.repository.ICitiesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FindConnectionBetweenCitiesImplTest  {

    @Autowired
    FindConnectionBetweenCitiesImpl findConnectionBetweenCities;

    @MockBean
    ICitiesRepository repository;

    @BeforeEach
    public void setup(){
        findConnectionBetweenCities.setCityAndClusterMap((new HashMap<>()));
        findConnectionBetweenCities.setClusterAndCityMap((new HashMap()));
    }


    @Test
    public void fillUpMapsTestUnconnectedCitiesSet(){
        findConnectionBetweenCities.fillUpMaps(new String[]{"A","B"},0);
        findConnectionBetweenCities.fillUpMaps(new String[]{"C","D"},1);
        findConnectionBetweenCities.fillUpMaps(new String[]{"E","F"},2);

        Map<String,String> cityAndClusterMap = findConnectionBetweenCities.getCityAndClusterMap();
        Map<String, Set<String>> clusterAndCityMap = findConnectionBetweenCities.getClusterAndCityMap();


        assertEquals(cityAndClusterMap.get("A"),"cluster0");
        assertEquals(cityAndClusterMap.get("B"),"cluster0");
        assertEquals(cityAndClusterMap.get("C"),"cluster1");
        assertEquals(cityAndClusterMap.get("D"),"cluster1");
        assertEquals(cityAndClusterMap.get("E"),"cluster2");
        assertEquals(cityAndClusterMap.get("F"),"cluster2");

        Set<String> cluster0Set = clusterAndCityMap.get("cluster0");

        assertTrue(cluster0Set.contains("A"));
        assertTrue(cluster0Set.contains("B"));
        assertEquals(cluster0Set.size(),2);

        Set<String> cluster1Set = clusterAndCityMap.get("cluster1");

        assertTrue(cluster1Set.contains("C"));
        assertTrue(cluster1Set.contains("D"));
        assertEquals(cluster0Set.size(),2);

        Set<String> cluster2Set = clusterAndCityMap.get("cluster2");

        assertTrue(cluster2Set.contains("E"));
        assertTrue(cluster2Set.contains("F"));
        assertEquals(cluster2Set.size(),2);
    }

    @Test
    public void fillUpMapsSingleconnectedCitiesSetTest(){
        findConnectionBetweenCities.fillUpMaps(new String[]{"A","B"},0);
        findConnectionBetweenCities.fillUpMaps(new String[]{"C","D"},1);
        findConnectionBetweenCities.fillUpMaps(new String[]{"D","F"},2);

        Map<String,String> cityAndClusterMap = findConnectionBetweenCities.getCityAndClusterMap();
        Map<String, Set<String>> clusterAndCityMap = findConnectionBetweenCities.getClusterAndCityMap();


        assertEquals(cityAndClusterMap.get("A"),"cluster0");
        assertEquals(cityAndClusterMap.get("B"),"cluster0");
        assertEquals(cityAndClusterMap.get("C"),"cluster1");
        assertEquals(cityAndClusterMap.get("D"),"cluster1");
        assertEquals(cityAndClusterMap.get("F"),"cluster1");

        Set<String> cluster0Set = clusterAndCityMap.get("cluster0");

        assertTrue(cluster0Set.contains("A"));
        assertTrue(cluster0Set.contains("B"));
        assertEquals(cluster0Set.size(),2);

        Set<String> cluster1Set = clusterAndCityMap.get("cluster1");

        assertTrue(cluster1Set.contains("C"));
        assertTrue(cluster1Set.contains("D"));
        assertTrue(cluster1Set.contains("F"));

        assertEquals(cluster1Set.size(),3);
    }

    @Test
    public void fillUpMapsDoubleconnectedCitiesSetTest(){
        findConnectionBetweenCities.fillUpMaps(new String[]{"A","B"},0);
        findConnectionBetweenCities.fillUpMaps(new String[]{"C","D"},1);
        findConnectionBetweenCities.fillUpMaps(new String[]{"D","A"},2);

        Map<String,String> cityAndClusterMap = findConnectionBetweenCities.getCityAndClusterMap();
        Map<String, Set<String>> clusterAndCityMap = findConnectionBetweenCities.getClusterAndCityMap();


        assertEquals(cityAndClusterMap.get("A"),"cluster1");
        assertEquals(cityAndClusterMap.get("B"),"cluster1");
        assertEquals(cityAndClusterMap.get("C"),"cluster1");
        assertEquals(cityAndClusterMap.get("D"),"cluster1");


        Set<String> cluster1Set = clusterAndCityMap.get("cluster1");

        assertTrue(cluster1Set.contains("A"));
        assertTrue(cluster1Set.contains("B"));
        assertTrue(cluster1Set.contains("C"));
        assertTrue(cluster1Set.contains("D"));
        assertEquals(cluster1Set.size(),4);
    }

    @Test
    public void fillUpMapsDuplicateEntriesTest(){
        findConnectionBetweenCities.fillUpMaps(new String[]{"A","B"},0);
        findConnectionBetweenCities.fillUpMaps(new String[]{"A","B"},1);

        Map<String,String> cityAndClusterMap = findConnectionBetweenCities.getCityAndClusterMap();
        Map<String, Set<String>> clusterAndCityMap = findConnectionBetweenCities.getClusterAndCityMap();


        assertEquals(cityAndClusterMap.get("A"),"cluster0");
        assertEquals(cityAndClusterMap.get("B"),"cluster0");

        Set<String> cluster0Set = clusterAndCityMap.get("cluster0");

        assertTrue(cluster0Set.contains("A"));
        assertTrue(cluster0Set.contains("B"));
        assertEquals(cluster0Set.size(),2);
    }

    @Test
    public void fillUpMapsReverseOrderEntriesTest(){
        findConnectionBetweenCities.fillUpMaps(new String[]{"A","B"},0);
        findConnectionBetweenCities.fillUpMaps(new String[]{"B","A"},1);

        Map<String,String> cityAndClusterMap = findConnectionBetweenCities.getCityAndClusterMap();
        Map<String, Set<String>> clusterAndCityMap = findConnectionBetweenCities.getClusterAndCityMap();


        assertEquals(cityAndClusterMap.get("A"),"cluster0");
        assertEquals(cityAndClusterMap.get("B"),"cluster0");

        Set<String> cluster0Set = clusterAndCityMap.get("cluster0");

        assertTrue(cluster0Set.contains("A"));
        assertTrue(cluster0Set.contains("B"));
        assertEquals(cluster0Set.size(),2);
    }

    @Test
    public void findIfCitiesAreConnectedPositiveTestCase(){
        String[] connectedCities1 = new String[]{"A","B"};
        String[] connectedCities2 = new String[]{"B","C"};
        List<String[]> connectionsList = new ArrayList<>();
        connectionsList.add(connectedCities1);
        connectionsList.add(connectedCities2);

        when(repository.getCitiesData()).thenReturn(connectionsList);

        assertEquals(findConnectionBetweenCities.findIfCitiesAreConnected("A","C"),"yes");

    }


    @Test
    public void findIfCitiesAreConnectedNeativeTestCase(){
        String[] connectedCities1 = new String[]{"A","B"};
        String[] connectedCities2 = new String[]{"C","D"};
        List<String[]> connectionsList = new ArrayList<>();
        connectionsList.add(connectedCities1);
        connectionsList.add(connectedCities2);

        when(repository.getCitiesData()).thenReturn(connectionsList);

        assertEquals(findConnectionBetweenCities.findIfCitiesAreConnected("A","D"),"no");

    }

    @Test
    public void findIfCitiesAreConnectedInvalidCity(){
        String[] connectedCities1 = new String[]{"A","B"};
        String[] connectedCities2 = new String[]{"B","D"};
        List<String[]> connectionsList = new ArrayList<>();
        connectionsList.add(connectedCities1);
        connectionsList.add(connectedCities2);

        when(repository.getCitiesData()).thenReturn(connectionsList);

        assertEquals(findConnectionBetweenCities.findIfCitiesAreConnected("X","Y"),"no");

    }



}
