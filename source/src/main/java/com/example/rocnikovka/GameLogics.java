package com.example.rocnikovka;

public class GameLogics {
    private int[][] playerOneMap;
    private int[][] playerTwoMap;

    private int playerOneAliveShips;
    private int playerTwoAliveShips;

    private int gameStatus;

    // MAP KEYS
    // 0 = SEA
    // 1 = SHIP
    // 2 = HIT
    // 3 = DEAD SHIP

    // GAME STATUS KEYS
    // 0 = PLAYER 1 CHOOSING
    // 1 = PLAYER 2 CHOOSING
    // 2 = PLAYER 1 SHOOTING
    // 3 = PLAYER 2 SHOOTING
    // 4 = GAME OVER

    public GameLogics() {

        // Initialize variables
        this.playerOneMap = new int[10][10];
        this.playerTwoMap = new int[10][10];
        this.playerOneAliveShips = this.playerTwoAliveShips = 0;

        // Clear maps
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                this.playerOneMap[x][y] = this.playerTwoMap[x][y] = 0;
            }
        }

        gameStatus = 0;

    }

    // CHOOSE RETURN KEYS
    // 1 = SHIP ADDED
    // 2 = PLAYER 1 CHOSE ALL SHIPS
    // 3 = PLAYER 2 CHOSE ALL SHIPS
    // -1 = SHIP IS ALREADY AT THIS LOCATION
    // -2 = OTHER SHIP IS NEARBY
    public int chooseShip(int x, int y, boolean playerOne) {
        if (playerOne) {
            if (this.playerOneMap[x][y] == 1)
                return -1;
            if ((x == 0 ? 0 : this.playerOneMap[x - 1][y]) == 1 ||
                    (x == 9 ? 0 : this.playerOneMap[x + 1][y]) == 1 ||
                    (y == 0 ? 0 : this.playerOneMap[x][y - 1]) == 1 ||
                    (y == 9 ? 0 : this.playerOneMap[x][y + 1]) == 1)
                return -2;
            this.playerOneMap[x][y] = 1;
            this.playerOneAliveShips++;
        } else {
            if (this.playerTwoMap[x][y] == 1)
                return -1;
            if ((x == 0 ? 0 : this.playerTwoMap[x - 1][y]) == 1 ||
                    (x == 9 ? 0 : this.playerTwoMap[x + 1][y]) == 1 ||
                    (y == 0 ? 0 : this.playerTwoMap[x][y - 1]) == 1 ||
                    (y == 9 ? 0 : this.playerTwoMap[x][y + 1]) == 1)
                return -2;
            this.playerTwoMap[x][y] = 1;
            this.playerTwoAliveShips++;
        }
        if (this.playerOneAliveShips == 10 && this.gameStatus == 0) {
            updateGameStatus(1);
            return 2;
        }
        if (this.playerTwoAliveShips == 10 && this.gameStatus == 1) {
            updateGameStatus(2);
            return 3;
        }
        return 1;
    }

    public void updateGameStatus(int newStatus) {
        this.gameStatus = newStatus;
    }

    public int getGameStatus() {
        return this.gameStatus;
    }

    public int getPlayerOneAliveShips() {
        return this.playerOneAliveShips;
    }

    public int getPlayerTwoAliveShips() {
        return this.playerTwoAliveShips;
    }

    // SHOOT RETURN KEYS
    // 0 = NOTHING HAPPENED
    // 1 = ALREADY HIT / MISSED
    // 2 = MISSED
    // 3 = HIT / KILLED
    // 4 = HIT / PLAYER 1 WON
    // 5 = HIT / PLAYER 2 WON
    public int shoot(int x, int y, boolean playerOne) {

        int output = 0;

        System.out.println("Status at loc P1:" + this.playerOneMap[x][y] + " P2:" + this.playerTwoMap[x][y]);

        // MAKE SHOOT
        switch (playerOne ? this.playerTwoMap[x][y] : this.playerOneMap[x][y]) {
            case 0: // PLAYER MISSED
                output = 2;
                if (playerOne)
                    this.playerTwoMap[x][y] = 2;
                else
                    this.playerOneMap[x][y] = 2;
                break;
            case 1: // PLAYER KILLED SHIP
                output = 3;
                if (playerOne) {
                    this.playerTwoMap[x][y] = 3;
                    this.playerTwoAliveShips--;
                } else {
                    this.playerOneMap[x][y] = 3;
                    this.playerOneAliveShips--;
                }
                break;
            default: // IF PLAYER ALREADY SHOOT AT THIS LOC
                output = 1;
                return output;
        }

        // CHECK IF PLAYER WON
        if (this.playerOneAliveShips == 0) {
            this.updateGameStatus(5);
            return 5;
        }
        if (this.playerTwoAliveShips == 0) {
            this.updateGameStatus(4);
            return 4;
        }

        // UPDATE GAME STATUS
        if(output != 3){
            if(playerOne)
                this.updateGameStatus(3);
            else
                this.updateGameStatus(2);
        }

        return output;

    }
}
