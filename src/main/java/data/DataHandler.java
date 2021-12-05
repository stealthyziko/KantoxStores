package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import rules.OneGetTwo;
import rules.Rule;
import rules.ThreeFor0_9Price;
import rules.ThreeForTwoThirds;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class DataHandler {
    public static final String DEFAULTXML = "src/main/resources/articles.xml";
    private Serializer serializer;
    private File source;
    private boolean valid = false;
    private static Logger log = LogManager.getLogger(DataHandler.class);
    private Map<String, Article> articleMap;
    private Thread watcherThread;
    private Map<String, Rule> ruleMap;


    public DataHandler(){
        this(DEFAULTXML);
    }

    public DataHandler(String xmlPath){
        serializer = new Persister();
        source = new File(xmlPath);
        mapArticles();
        mapRules();
        startFileWatcher();
    }

    public void startFileWatcher(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    watch();
                } catch (IOException | InterruptedException e) {
                    log.warn("XML File watcher stopped working", e);
                }
            }
        };
        watcherThread = new Thread(runnable);
        watcherThread.start();
    }

    public void stopFileWatcher(){
        watcherThread.interrupt();
    }

    public void mapArticles(){
        Articles articleList = null;
        try {
            articleList = serializer.read(Articles.class, source);
        } catch (Exception e) {
            log.fatal("Reading the articles XML was unsuccessful", e);
            return;
        }
        articleMap = new HashMap<String, Article>(articleList.getArticles().size()*2, 0.75f);
        for (Article article:articleList.getArticles()
        ) {
            articleMap.put(article.getCode(), article);
        }
        valid = true;
    }

    private void mapRules(){
        ruleMap = new HashMap<String, Rule>();
        ruleMap.put("OneGetTwo", new OneGetTwo());
        ruleMap.put("ThreeFor0_9Price", new ThreeFor0_9Price());
        ruleMap.put("ThreeForTwoThirds", new ThreeForTwoThirds());
    }

    public Map<String, Rule> getRuleMap(){
        return ruleMap;
    }

    private void watch() throws IOException, InterruptedException {
        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        Path path = FileSystems.getDefault().getPath(source.getParentFile().getAbsolutePath());

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                if(event.context().toString().equals(source.getPath().substring(source.getParent().length()+1))){
                    mapArticles();
                    log.info("Article Data XML has been modified");
                }
            }
            key.reset();
        }
    }

    public Article getByCode(String code){
        return articleMap.get(code);
    }
}
