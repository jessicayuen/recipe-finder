/**
 * Holds elastic search response times and data.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.elasticsearch;

import java.util.ArrayList;
import java.util.Collection;

public class ElasticSearchSearchResponse<T> {
    int took;
    boolean timed_out;
    transient Object _shards;
    Hits<T> hits;
    boolean exists;    
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits.getHits();        
    }
    
    /**
     * Retrieve the sources from elastic search
     * @return
     */
    public Collection<T> getSources() {
        Collection<T> out = new ArrayList<T>();
        for (ElasticSearchResponse<T> essrt : getHits()) {
            out.add( essrt.getSource() );
        }
        return out;
    }
    
    /**
     * Returns a string result
     */
    public String toString() {
        return (super.toString() + ":" + took + "," + _shards + 
        		"," + exists + ","  + hits);     
    }
}
