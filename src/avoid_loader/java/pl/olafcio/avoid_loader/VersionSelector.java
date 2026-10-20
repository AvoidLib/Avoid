package pl.olafcio.avoid_loader;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public class VersionSelector {
    protected IntArrayList parseVersion(String text) {
        var parts = new IntArrayList();
        var value = new StringBuilder();

        text.codePoints().forEachOrdered(ch -> {
            if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                value.append(ch);
            } else {
                if (!value.isEmpty()) {
                    parts.add(Integer.parseInt(value.toString(), 36));
                    value.setLength(0);
                }
            }
        });

        if (!value.isEmpty())
            parts.add(Integer.parseInt(value.toString(), 36));

        return parts;
    }

    public enum VersionSide {
        A, B
    }

    public VersionSide pickVersion(String a, String b) {
        var parsedA = parseVersion(a);
        var parsedB = parseVersion(b);

        int i = 0;

        for (var el : parsedB) {
            if (parsedA.size() < i+1 || parsedA.getInt(i) < el)
                return VersionSide.B;

            i++;
        }

        return VersionSide.A;
    }
}
