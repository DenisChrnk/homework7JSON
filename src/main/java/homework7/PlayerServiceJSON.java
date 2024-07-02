package homework7;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;


public class PlayerServiceJSON implements PlayerService {

    @Override
    public Player getPlayerById(int id) throws IOException {
        List<Player> allPlayers = getPlayers().stream().toList();
        for (Player pl : allPlayers) {
            if (pl.getId() == id)
                return pl;
        }
        return null;
    }

    @Override
    public Collection<Player> getPlayers() throws IOException {
        Path filePath = Path.of("player.json");
        ObjectMapper mapper = new ObjectMapper();
        File newFile = new File("./player.json");
        if (newFile.length() == 0) {
            FileWriter fWrite = new FileWriter(newFile, true);
            fWrite.write("[]");
            fWrite.close();
        }
        List<Player> getPl = mapper.readValue(filePath.toFile(), new TypeReference<>() {
        });
        return getPl;
    }

    @Override
    public int createPlayer(String nickname) throws IOException {
        Path filePath = Path.of("player.json");
        ObjectMapper mapper = new ObjectMapper();
        Collection<Player> listPlayer = getPlayers();

        if (listPlayer.size() - 1 == -1) {
            int i = 1;
            List<Player> allPl = new ArrayList<>(listPlayer.stream().toList());
            Player players = new Player(i, nickname, 0, true);
            allPl.add(players);
            mapper.writeValue(filePath.toFile(), allPl);
            return players.getId();
        }
        int a = listPlayer.size() - 1;
        Player b = listPlayer.stream().toList().get(a);
        int i = b.getId() + 1;
        List<Player> allPl = new ArrayList<>(listPlayer.stream().toList());
        Player players = new Player(i, nickname, 0, true);
        allPl.add(players);
        mapper.writeValue(filePath.toFile(), allPl);
        return players.getId();
    }

    @Override
    public Player deletePlayer(int id) throws IOException {
        Path filePath = Path.of("player.json");
        ObjectMapper mapper = new ObjectMapper();
        Collection<Player> allPlayerForDel = getPlayers();
        Player playerForDel = null;
        for (Player delPlayer : allPlayerForDel) {
            if (delPlayer.getId() == id) {
                playerForDel = delPlayer;
            }
        }
        allPlayerForDel.remove(playerForDel);
        mapper.writeValue(filePath.toFile(), allPlayerForDel);
        return playerForDel;

    }

    @Override
    public int addPoints(int playerId, int points) throws IOException {
        Path filePath = Path.of("player.json");
        ObjectMapper mapper = new ObjectMapper();
        Collection<Player> allPlayerAddPoints = getPlayers();
        Player addPointPlayer = null;
        for (Player playerForAdd : allPlayerAddPoints) {
            if (playerForAdd.getId() == playerId) {
                addPointPlayer = playerForAdd;
            }
        }
        addPointPlayer.setPoints(points);
        mapper.writeValue(filePath.toFile(), allPlayerAddPoints);
        return addPointPlayer.getPoints();
    }
}
