// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Bitmap.java is a part of organization moe-org, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021-2023 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.graphics.yongle;

/**
 * 位图
 */
public class Bitmap {

    private int x;

    private int y;

    private ColorPoint[][] data;

    public Bitmap(int x, int y) {
        reset(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * assess as data[x][y]
     * @return
     */
    public ColorPoint[][] getData() {
        return data;
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;

        data = new ColorPoint[x][];

        for (int i = 0; i <= x; i++) {
            data[i] = new ColorPoint[y];

            for (int j = 0; j <= y; j++) {
                data[i][j] = new ColorPoint();
            }
        }
    }
}
