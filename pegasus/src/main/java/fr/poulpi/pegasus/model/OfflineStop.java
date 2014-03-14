package fr.poulpi.pegasus.model;

import java.util.List;

/**
 * Created by pokito on 09/02/14.
 */
public class OfflineStop {

    public int stop_id;
    public String name;
    public OfflineLoc loc;
    public int zip;
    public List<OfflineEdge> edges;


}
