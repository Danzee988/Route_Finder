import Methods.Stations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StationsTest {

    @Test
    public void testGetters() {
        Stations station = new Stations(1, "Station 1", 10.0f, 20.0f);

        Assertions.assertEquals(1, station.getId());
        Assertions.assertEquals("Station 1", station.getName());
        Assertions.assertEquals(10.0f, station.getLongitude());
        Assertions.assertEquals(20.0f, station.getLatitude());
    }

    @Test
    public void testSetters() {
        Stations station = new Stations(1, "Station 1", 10.0f, 20.0f);

        station.setId(2);
        station.setName("Station 2");
        station.setLongitude(30.0f);
        station.setLatitude(40.0f);

        Assertions.assertEquals(2, station.getId());
        Assertions.assertEquals("Station 2", station.getName());
        Assertions.assertEquals(30.0f, station.getLongitude());
        Assertions.assertEquals(40.0f, station.getLatitude());
    }

    @Test
    public void testToString() {
        Stations station = new Stations(1, "Station 1", 10.0f, 20.0f);

        String expected = "Stations{ id= 1, name= Station 1, longitude= 10.0, latitude= 20.0 }";
        Assertions.assertEquals(expected, station.toString());
    }

    @Test
    public void testPathName() {
        Stations station = new Stations(1, "Station 1", 10.0f, 20.0f);

        Assertions.assertEquals("Station 1", station.pathName());
    }
}
