
attribute vec3 aPosition;
attribute vec2 aTexcoord;

varying vec2 vTexcoord;

uniform vec2 uScreenSize;
uniform vec2 uPosition;
uniform vec4 uTexcoord;

void main()
{
	vec2 position = 2.0 * (aPosition.xy + uPosition) / uScreenSize;
	position = position - vec2(1.0,1.0);
	
	gl_Position = vec4(position, 0.0, 1.0);
	vTexcoord = aTexcoord * uTexcoord.pq + uTexcoord.st;
}
