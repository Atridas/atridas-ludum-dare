
attribute vec2 aPosition;

varying vec2 vTexcoord;

uniform vec2 uScreenSize;
uniform vec4 uPosition;
uniform vec4 uTexCoord;

void main()
{
	vec2 position = 2.0 * (aPosition.xy * uPosition.zw + uPosition.xy) / uScreenSize;
	position = position - vec2(1.0,1.0);
	
	vec2 texCoord = vec2(aPosition.s, 1.0 - aPosition.t);
	
	gl_Position = vec4(position, 0.0, 1.0);
	vTexcoord = texCoord * uTexCoord.pq + uTexCoord.st;
}
