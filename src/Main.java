public class Main {
    public static void main(String[] args) {
        tests();
    }
    public static void tests(){

        int[][] tests = {
                {
                    50, 25, 75, 10, 30, 60, 90, 5, 15, 27, 35, 55, 65, 80, 100, 1, 7, 12, 20, 33
                },
                {
                    20, 10, 30, 5, 15, 25, 35, 1, 7, 12, 18, 22, 28, 32, 40, 2, 3, 6, 13, 17,43
                },
                {
                            100, 50, 150, 25, 75, 125, 175, 10, 30, 60,
                            90, 110, 140, 160, 190, 5, 15, 35, 65, 85
                },
                {
                            1, 20, 2, 19, 3, 18, 4, 17, 5, 16,
                            6, 15, 7, 14, 8, 13, 9, 12, 10, 11
                },
                {
                            41, 38, 31, 12, 19, 8, 25, 50, 60, 1,
                            7, 15, 22, 27, 35, 45, 55, 65, 10, 30
                }
            };

            for (int i = 0; i < tests.length; i++) {

                RedBlackTree tree = new RedBlackTree();

                for (int value : tests[i]) {
                    tree.add(value);
                }

                tree.remove(50);
                tree.remove(25);
                tree.remove(30);
                tree.remove(12);
                tree.remove(55);

                System.out.println("unit test says "+ tree.unitTest());
                System.out.println("Test " + (i + 1));
                System.out.println(tree.inOrder());
                System.out.println(tree.preOrder());
                System.out.println(tree.postOrder());

        }
    }
}