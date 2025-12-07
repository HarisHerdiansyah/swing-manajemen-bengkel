/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package view.component;

public class ComboBoxItem {
    private String label;
    private Object value;

    public ComboBoxItem(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;
    }
}
