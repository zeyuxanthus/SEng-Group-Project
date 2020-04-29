import java.io.*;
import java.util.ArrayList;

public class States {

    ObjectOutputStream o;

    public void save(String filename, ArrayList<Click> clicks, ArrayList<ServerEntry> serverEntries, ArrayList<Impression> impressions){
        try{
            FileOutputStream f = new FileOutputStream(new File(filename + ".txt"));
            o = new ObjectOutputStream(f);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        ArrayList<Object> campaign = new ArrayList<>();
        for(Click c: clicks){
            campaign.add(c);
        }
        for(ServerEntry s : serverEntries){
            campaign.add(s);
        }
        for(Impression i : impressions) {
            campaign.add(i);
        }

        write(o, campaign);
    }

    public void write(ObjectOutputStream o, Object a){
        try{
            o.reset();
            o.writeObject(a);
            o.flush();
            o.close();
        } catch (IOException e ){
            e.printStackTrace();
        }
    }

    public ArrayList<Object> loadCampaign(String filename){
        try{
            FileInputStream fileInputStream = new FileInputStream(filename + ".txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Object> campaign = (ArrayList) objectInputStream.readObject();
            objectInputStream.close();
            return campaign;
        } catch (FileNotFoundException e ){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}
