/**
 * Records the hits of a Elastic Search Response result
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;
import java.util.Collection;


public class Hits<T> {
    int total;
    double max_score;
    Collection<ElasticSearchResponse<T>> hits;
    
    /**
     * Return the hits
     * @return Hits
     */
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits;
    }
    
    /**
     * Convert the hits to a string
     */
    public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}