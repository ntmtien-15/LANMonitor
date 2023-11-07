package Model;

public class DieuKhienClient extends PacketTin {
    public static final String ID = "remotedesktop";
    public static final String CMD_CHAPNHAN = "chapnhan";
    public static final String CMD_KHOIDONG = "khoidong";
    public static final String CMD_HOANTAT = "hoantat";
    public DieuKhienClient() {
        setId(ID);
    }
}
