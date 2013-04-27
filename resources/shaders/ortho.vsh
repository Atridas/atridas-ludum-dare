
attribute vec3 aPosition;
attribute vec4 aColor;
attribute vec2 aTexcoord;

varying vec4 vColor;
varying vec2 vTexcoord;

uniform vec2 uScreenSize;
uniform vec2 uPosition;

void main()
{
	vec2 position = 2.0 * (aPosition.xy + uPosition) / uScreenSize;
	position = position - vec2(1.0,1.0);
	
	gl_Position = vec4(position, 0.0, 1.0);
	vColor = aColor;
	vTexcoord = aTexcoord;
}
