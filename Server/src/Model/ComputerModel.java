package Model;

public class ComputerModel {
    private String ip;
    private String name;
    private int port;

    public ComputerModel() {
    }
    public ComputerModel(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }
    
    public String getIp() {
        return ip;
    }

    //param ip the ip to set
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

   //param name the name to set
    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    //param port the port to set
    public void setPort(int port) {
        this.port = port;
    }
}
