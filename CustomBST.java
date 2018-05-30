public class CustomBST {

    private CustomBSTNode root;

    public CustomBST() {
        this.root = null;
    }

    public void insert(String k, TennisPlayerNode d) {
        insertRec(k, d, this.root);
    }

    public void insertPlayer(TennisPlayerNode player) throws TennisDatabaseRuntimeException {
        if (!search(player.getPlayer().getId())) {
            insert(player.getPlayer().getId(), player);
        } else {
            try {
                throw new TennisDatabaseRuntimeException("Duplicate player");
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("Duplicate player found, continuing");
            }
        }
    }

    public void insertMatch(TennisMatch match) throws TennisDatabaseRuntimeException {
        // TODO
        // Need to implement a method to return the tennis player node so I can insert a match
        if (!search(match.getPlayer1Id())) {
            TennisPlayer player = new TennisPlayer(match.getPlayer1Id(),
                    "", "", 0, "");
            player.setDummy(true);
            try {
                TennisPlayerNode playerNode = new TennisPlayerNode(player);
                insertPlayer(playerNode);
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("Problem inserting player One.");
                player.print();
            }
            nodePlayer1 = getPlayerNode(match.getPlayer1Id());
        }
        if (nodePlayer1 != null) {
            nodePlayer1.insertMatch(match);
        }

        if (!search(match.getPlayer2Id())) {
            TennisPlayer player2 = new TennisPlayer(match.getPlayer2Id(),
                    "", "", 0, "");
            player2.setDummy(true);
            try {
                insertPlayer(player2);
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("Problem inserting player Two.");
                player2.print();
            }
            nodePlayer2 = getPlayerNode(match.getPlayer2Id());
        }
        if (nodePlayer2 != null) {
            nodePlayer2.insertMatch(match);
        }
    }

    public CustomBSTNode insertRec(String k, TennisPlayerNode d, CustomBSTNode currRoot) {
        if (currRoot == null) {
            currRoot = new CustomBSTNode(k, d);
            return currRoot;
        } else {
            if (currRoot.getKey().equals(k)) {
                return null;
            } else if (currRoot.getKey().compareTo(k) < 0) {
                CustomBSTNode newRoot = insertRec( k, d, currRoot.getRightChild());
                currRoot.setRightChild(newRoot);
                return currRoot;
            } else {
                CustomBSTNode newRoot = insertRec( k, d, currRoot.getLeftChild());
                currRoot.setLeftChild(newRoot);
                return currRoot;
            }
        }
    }

    public CustomBSTNode findRightMost( CustomBSTNode currRoot) {
        if( currRoot == null) {
            return null;
        } else {
            if(currRoot.hasRightChild()) {
                return findRightMost(currRoot.getRightChild());
            } else {
                return currRoot;
            }
        }
    }

    public CustomBSTNode findLeftMost( CustomBSTNode currRoot) {
        if( currRoot == null) {
            return null;
        } else {
            if(currRoot.hasLeftChild()) {
                return findLeftMost(currRoot.getLeftChild());
            } else {
                return currRoot;
            }
        }
    }

    public boolean search( String k) {
        return searchRec( k, this.root);
    }

    private boolean searchRec(String k, CustomBSTNode currRoot) {
        if (currRoot == null) {
            return false;
        } else {
            if( currRoot.getKey().equals(k)) {
                return true;
            } else if (currRoot.getKey().compareTo(k) < 0) {
                return searchRec(k, currRoot.getRightChild());
            } else {
                return searchRec(k, currRoot.getLeftChild());
            }
        }
    }
}
