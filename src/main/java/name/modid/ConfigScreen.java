package name.modid;


import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "jungles-universal-challenges")
@Config(name = "jungleconfig", wrapperName = "WrConfigScreen")
public class ConfigScreen {
    public boolean air_challenge = false;
    public boolean hunger_challenge = false;
    public boolean light_challenge = false;
    public boolean light_challenge_pure = false;
    public boolean xp_challenge = false;

}