public class Mahasiswa {
    private int id ;
    private String nama = null ;
    private String nim = null;
    private String jurusan = null ;
    
    public Mahasiswa(int inputId, String inputNama, String inputNim, String inputJurusan){
        this.id = inputId;
        this.nama = inputNama;
        this.nim = inputNim;
        this.jurusan = inputJurusan;
        
    }
    
    public int getId() {
        return id;
    }
    public String getNama(){
        return nama;
    }
    public String getNim(){
        return nim;
    }
    public String getJurusan(){
        return jurusan;
    }

}