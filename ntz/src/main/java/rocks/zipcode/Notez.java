package rocks.zipcode;

import rocks.zipcode.FileMap;
import rocks.zipcode.NoteList;

/**
 * ntz main command.
 */
public final class Notez {

    private FileMap filemap;

    public Notez() {
        this.filemap  = new FileMap();
    }
    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     */
    public static void main(String[] argv) {
        boolean _debug = true;
        // for help in handling the command line flags and data!
        if (_debug) {
            System.err.print("Argv: [");
            for (String a : argv) {
                System.err.print(a+" ");
            }
            System.err.println("]");
        }

        Notez ntzEngine = new Notez();

        ntzEngine.loadDatabase();

        /*
         * You will spend a lot of time right here.
         *
         * instead of loadDemoEntries, you will implement a series
         * of method calls that manipulate the Notez engine.
         * See the first one:
         */
//        ntzEngine.loadDemoEntries();
//        ntzEngine.saveDatabase();

        if (argv.length == 0) { // there are no commandline arguments
            //just print the contents of the filemap.
            ntzEngine.printResults();
        } else {
            if (argv[0].equals("-r")) {
                ntzEngine.addToCategory("General", argv);
            } // this should give you an idea about how to TEST the Notez engine
              // without having to spend lots of time messing with command line arguments.
            else if(argv[0].equals("-c")){
                ntzEngine.createCategory(argv);
            }
            else if(argv[0].equals("-e")){
                ntzEngine.editNote(argv);
            }
            else if(argv[0].equals("-f")){
                ntzEngine.forget(argv);
            }
        }
        /*
         * what other method calls do you need here to implement the other commands??
         */
        ntzEngine.saveDatabase();
        ntzEngine.printResults();

    }

    private void saveDatabase() {
        filemap.save();
    }

    private void loadDatabase() {
        filemap.load();
    }

    public void printResults() {
        System.out.println(this.filemap.toString());
    }

    public void loadDemoEntries() {
        filemap.put("General", new NoteList("The Very first Note"));
        filemap.put("note2", new NoteList("A secret second note"));
        filemap.put("category3", new NoteList("Did you buy bread AND eggs?"));
        filemap.put("anotherNote", new NoteList("Hello from ZipCode!"));
    }


    /*
     * Put all your additional methods that implement commands like forget here...
     */

    public boolean addToCategory(String string, String[] argv) {
        if(filemap.containsKey(string)){
            filemap.get(string).add(argv[1]);
            return true;
        }
        else{
            NoteList newNote = new NoteList(argv[1]);
            filemap.put(string, newNote);
            return true;
        }
    }

    public boolean createCategory(String[] args){
        NoteList noteList = new NoteList(args[2]);
        filemap.put(args[1], noteList);
        return true;
    }

    public boolean editNote(String[] args){
        // CHANGING THE FORMAT FOR EDIT TO:
        // -e category notenumber "text to replace it with"

        if(filemap.containsKey(args[1])){
            NoteList noteList = filemap.get(args[1]);
            int noteNumber = Integer.parseInt(args[2]) - 1;
            noteList.set(noteNumber, args[3]);
            filemap.put(args[1], noteList);
            return true;
        }
        return false;
    }

    public boolean forget(String[] args) {
        // changing command format to:
        // -f category noteNumber
        if(filemap.containsKey(args[1])){
            NoteList noteList = filemap.get(args[1]);
            noteList.remove(noteList.get(Integer.parseInt(args[2]) - 1));
            if(noteList.isEmpty()){
                filemap.remove(args[1]);
            }
            else{
                filemap.put(args[1], noteList);
            }
            return true;
        }
        return false;
    }

}
