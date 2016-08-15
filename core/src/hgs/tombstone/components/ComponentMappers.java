package hgs.tombstone.components;


import com.badlogic.ashley.core.ComponentMapper;

public class ComponentMappers {
	public static ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
	public static ComponentMapper<BitmapFontComponent> bitmapfont = ComponentMapper.getFor(BitmapFontComponent.class);
	public static ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
	public static ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);
	public static ComponentMapper<StateComponent> state = ComponentMapper.getFor(StateComponent.class);
	public static ComponentMapper<ClickComponent> click = ComponentMapper.getFor(ClickComponent.class);
	public static ComponentMapper<TweenComponent> tween = ComponentMapper.getFor(TweenComponent.class);
	public static ComponentMapper<TextButtonComponent> textbutton = ComponentMapper.getFor(TextButtonComponent.class);
	public static ComponentMapper<TickerComponent> ticker = ComponentMapper.getFor(TickerComponent.class);
	public static ComponentMapper<MovementComponent> movement = ComponentMapper.getFor(MovementComponent.class);
	public static ComponentMapper<ScrollComponent> scroll = ComponentMapper.getFor(ScrollComponent.class);
	public static ComponentMapper<ShaderComponent> shader = ComponentMapper.getFor(ShaderComponent.class);
	public static ComponentMapper<ShaderTimeComponent> shadertime = ComponentMapper.getFor(ShaderTimeComponent.class);
	public static ComponentMapper<ColorInterpolationComponent> colorinterps = ComponentMapper.getFor(ColorInterpolationComponent.class);
	public static ComponentMapper<SoundComponent> sound = ComponentMapper.getFor(SoundComponent.class);
	public static ComponentMapper<JumpComponent> jump = ComponentMapper.getFor(JumpComponent.class);
	public static ComponentMapper<GunComponent> gun = ComponentMapper.getFor(GunComponent.class);
	public static ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
	public static ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
	public static ComponentMapper<SlideComponent> slide = ComponentMapper.getFor(SlideComponent.class);
	public static ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);
	public static ComponentMapper<GameStateComponent> gamestate = ComponentMapper.getFor(GameStateComponent.class);
	public static ComponentMapper<BossEventComponent> bossevent = ComponentMapper.getFor(BossEventComponent.class);
	public static ComponentMapper<BossComponent> boss = ComponentMapper.getFor(BossComponent.class);
	public static ComponentMapper<MiniBossComponent> miniboss = ComponentMapper.getFor(MiniBossComponent.class);
	public static ComponentMapper<ShakeComponent> shake = ComponentMapper.getFor(ShakeComponent.class);
	public static ComponentMapper<StopComponent> stop = ComponentMapper.getFor(StopComponent.class);
	public static ComponentMapper<BarrierComponent> barrier = ComponentMapper.getFor(BarrierComponent.class);
	public static ComponentMapper<ShiftComponent> shift = ComponentMapper.getFor(ShiftComponent.class);
	public static ComponentMapper<HealthBarComponent> healthbar = ComponentMapper.getFor(HealthBarComponent.class);
	public static ComponentMapper<MusicComponent> music = ComponentMapper.getFor(MusicComponent.class);
	public static ComponentMapper<EmitterComponent> emitter = ComponentMapper.getFor(EmitterComponent.class);
	public static ComponentMapper<DisableComponent> disable = ComponentMapper.getFor(DisableComponent.class);
	public static ComponentMapper<WaveComponent> wave = ComponentMapper.getFor(WaveComponent.class);
	public static ComponentMapper<SquareComponent> square = ComponentMapper.getFor(SquareComponent.class);
	public static ComponentMapper<DelayComponent> delay = ComponentMapper.getFor(DelayComponent.class);
	public static ComponentMapper<NinepatchComponent> ninepatch = ComponentMapper.getFor(NinepatchComponent.class);
	public static ComponentMapper<CountComponent> count = ComponentMapper.getFor(CountComponent.class);
	public static ComponentMapper<EndlessComponent> endless = ComponentMapper.getFor(EndlessComponent.class);
	public static ComponentMapper<SawtoothComponent> sawtooth = ComponentMapper.getFor(SawtoothComponent.class);
	public static ComponentMapper<HeadsUpDisplayComponent> hud = ComponentMapper.getFor(HeadsUpDisplayComponent.class);
	public static ComponentMapper<ControlButtonComponent> controlbutton = ComponentMapper.getFor(ControlButtonComponent.class);
	public static ComponentMapper<CharacterChangeEvent> charchange = ComponentMapper.getFor(CharacterChangeEvent.class);
	public static ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
}
