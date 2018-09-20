package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.JFileChooserFinder;
import org.assertj.swing.fixture.JFileChooserFixture;
// import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.view.FileManager;

public class TestFileManager {
    private static Robot robot;
    private FileManager fileManager;
    private String text;

//  NEEDS TO BE COMMENTED OUT FOR CI ONLY
//  @BeforeClass
//  public static void setUp() {
//      setUpRobot();
//  }

//  private static void setUpRobot() {
//      robot = BasicRobot.robotWithNewAwtHierarchy();
//  }

    // --- ACCEPTED FILES ---
    @Ignore
    @Test
    public void afterLoad() throws InterruptedException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                setText(fileManager.loadFile());

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();
        fileChooserFixture.cancel();

        methodThread.join();

        assertEquals(false, fileManager.accept(new File("Test.png")));
        assertEquals(true, fileManager.accept(new File("Test.pl")));
    }

    @Ignore
    @Test
    public void afterSave() throws InterruptedException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();
        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveFile(null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();
        fileChooserFixture.cancel();

        methodThread.join();

        assertEquals(false, fileManager.accept(new File("Test.png")));
        assertEquals(true, fileManager.accept(new File("Test.pl")));

    }

    @Ignore
    @Test
    public void afterSaveAs() throws InterruptedException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveAsFile(null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();
        fileChooserFixture.cancel();

        methodThread.join();

        assertEquals(false, fileManager.accept(new File("Test.png")));
        assertEquals(true, fileManager.accept(new File("Test.pl")));

    }

    @Ignore
    @Test
    public void afterExport() throws InterruptedException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.export(null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();
        fileChooserFixture.cancel();

        methodThread.join();

        assertEquals(true, fileManager.accept(new File("Test.png")));
        assertEquals(false, fileManager.accept(new File("Test.pl")));

    }

    // --- LOAD ---

    @Ignore
    @Test
    public void load() throws InterruptedException, IOException {
        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.pl");

        FileWriter writer = new FileWriter(file);
        writer.write("test.");
        writer.close();

        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                setText(fileManager.loadFile());

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();
        fileChooserFixture.selectFile(
                new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.pl"));
        fileChooserFixture.approve();

        methodThread.join();

        assertEquals("test.", getText());
        file.delete();
    }

    // --- SAVE ---
    @Ignore
    @Test
    public void save() throws InterruptedException, IOException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveFile("test.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();

        fileChooserFixture.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooserFixture.fileNameTextBox().setText("save");
        fileChooserFixture.approve();

        methodThread.join();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "save.pl");
        assertEquals(true, file.exists());
        file.delete();
    }

    @Ignore
    @Test
    public void saveExistingFile() throws InterruptedException, IOException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveFile("test.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();

        fileChooserFixture.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooserFixture.fileNameTextBox().setText("save");
        fileChooserFixture.approve();

        methodThread.join();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "save.pl");
        assertEquals(true, file.exists());

        Thread methodThread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveFile("test.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread2.start();
        methodThread2.join();
        assertEquals(true, file.exists());

        file.delete();
    }

    @Ignore
    @Test
    public void saveWithNameEnding() throws InterruptedException, IOException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveFile("test.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();

        fileChooserFixture.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooserFixture.fileNameTextBox().setText("save.pl");
        fileChooserFixture.approve();

        methodThread.join();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "save.pl");
        assertEquals(true, file.exists());
        file.delete();
    }

    // --- SAVE AS ---
    @Ignore
    @Test
    public void saveAs() throws InterruptedException, IOException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveAsFile("test.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();

        fileChooserFixture.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooserFixture.fileNameTextBox().setText("saveAs");
        fileChooserFixture.approve();

        methodThread.join();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "saveAs.pl");
        assertEquals(true, file.exists());
        file.delete();
    }

    @Ignore
    @Test
    public void saveAsExistingFile() throws InterruptedException, IOException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveAsFile("test.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();

        fileChooserFixture.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooserFixture.fileNameTextBox().setText("saveAs");
        fileChooserFixture.approve();

        methodThread.join();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "saveAs.pl");
        assertEquals(true, file.exists());

        Thread methodThread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveAsFile("test.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread2.start();
        JFileChooserFixture fileChooserFixture2 = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture2.requireVisible();

        fileChooserFixture2.selectFile(file);
        fileChooserFixture2.approve();

        methodThread2.join();

        assertEquals(true, file.exists());

        file.delete();
    }

    @Ignore
    @Test
    public void saveAsWithNameEnding() throws InterruptedException, IOException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.saveAsFile("test.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();

        fileChooserFixture.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooserFixture.fileNameTextBox().setText("saveAs.pl");
        fileChooserFixture.approve();

        methodThread.join();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "saveAs.pl");
        assertEquals(true, file.exists());
        file.delete();
    }

    // --- EXPORT ---
    @Ignore
    @Test
    public void export() throws InterruptedException, IOException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.export(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();

        fileChooserFixture.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooserFixture.fileNameTextBox().setText("image");
        fileChooserFixture.approve();

        methodThread.join();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "image.png");
        assertEquals(true, file.exists());
        file.delete();
    }

    @Ignore
    @Test
    public void exportWithNameEnding() throws InterruptedException, IOException {
        fileManager = FileManager.getInstance();
        fileManager.resetPath();

        Thread methodThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fileManager.export(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        methodThread.start();

        JFileChooserFixture fileChooserFixture = JFileChooserFinder.findFileChooser().withTimeout(1000).using(robot);
        fileChooserFixture.requireVisible();

        fileChooserFixture.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooserFixture.fileNameTextBox().setText("image.png");
        fileChooserFixture.approve();

        methodThread.join();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "image.png");
        assertEquals(true, file.exists());
        file.delete();
    }

    // -- ADDITIONAL STUFF ---
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
