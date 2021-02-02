package org.happykit.happyboot;

import org.happykit.happyboot.server.WebSocketServer;
import org.springframework.web.bind.annotation.*;

import javax.websocket.Session;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/23
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "This is websocket server ~";
    }

//    @GetMapping("/push")
//    public ResponseEntity<String> push(String msg,String userId) throws IOException {
//        WebSocketServer.sendInfo(msg,userId);
//        return ResponseEntity.ok("send success!");
//    }

    @RequestMapping("/getOnlineList")
    @ResponseBody
    public Set<String> getOnlineList(@RequestParam("currentuser") String currentuser) {
        ConcurrentHashMap<String, Session> map = WebSocketServer.getSessionPools();
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        Set<String> nameset = new HashSet<>();
        while (it.hasNext()) {
            String entry = it.next();
            if (!entry.equals(currentuser)) {
                nameset.add(entry);
            }
        }
        return nameset;
    }
}
