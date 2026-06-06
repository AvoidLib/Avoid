package pl.olafcio.avoid.net.item.stack;

import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.ItemNative;
import pl.olafcio.avoid.net.item.component.ItemComponentType;
import pl.olafcio.avoid.net.item.component.map.EditableItemComponentMap;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMap;

public final class ItemStack {
    private final Item item;
    private final EditableItemComponentMap components;
    private int amount;

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
        this.components = new EditableItemComponentMap(item.getComponents());
    }

    public ItemStack(Item item, int amount, ItemComponentMap components) {
        this.item = item;
        this.amount = amount;
        this.components = new EditableItemComponentMap(components);
    }

    public ItemStack(Item item, int amount, EditableItemComponentMap components) {
        this.item = item;
        this.amount = amount;
        this.components = components;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public ItemComponentMap getComponents() {
        return components;
    }

    public <T> T get(ItemComponentType<T> component) {
        return components.get(component);
    }

    public <T> void set(ItemComponentType<T> component, T value) {
        components.set(component, value);
    }

    public ItemStack withAmount(int value) {
        this.amount = value;
        return this;
    }

    public ItemStack copy() {
        return new ItemStack(item, amount, components.clone());
    }

    public static ItemStack fromID(Identification id) {
        var item = Item.of(id);
        return new ItemStack(item, 1);
    }

    public static ItemStack id(String id) {
        var item = Item.of(id);
        return new ItemStack(item, 1);
    }
}
