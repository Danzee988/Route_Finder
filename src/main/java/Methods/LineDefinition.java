package Methods;

public class LineDefinition {
    int station1ID;
    int station2ID;
    int lineID;

    //----------------Constructor----------------
    public LineDefinition(int station1ID, int station2ID, int lineID) {
        this.station1ID = station1ID;
        this.station2ID = station2ID;
        this.lineID = lineID;
    }



    //----------------Getters----------------
    public int getStation1ID() {
        return station1ID;
    }

    public int getStation2ID() {
        return station2ID;
    }

    public int getLineID() {
        return lineID;
    }



    //----------------Setters----------------
    public void setStation1ID(int station1ID) {
        this.station1ID = station1ID;
    }


    public void setStation2ID(int station2ID) {
        this.station2ID = station2ID;
    }


    public void setLineID(int lineID) {
        this.lineID = lineID;
    }


    @Override
    public String toString() {
        return "LineDefinition{" +
                "station1ID=" + station1ID +
                ", station2ID=" + station2ID +
                ", lineID=" + lineID +
                '}';
    }
}
