package pl.olafcio.avoid_impl.mixininterface;

public interface IPlayer {
    /** Please please PLEASE don't call the converter within the converter */
    void avoid$pleaseDontStackOverflow();
}
