
attribute vec3 aPosition;
attribute vec4 aColor;
attribute vec2 aTexcoord;

varying vec4 vColor;
varying vec2 vTexcoord;

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uModel;

void main()
{
	gl_Position = uProjection * uView * uModel * vec4(aPosition, 1.0);
	vColor = aColor;
	vTexcoord = aTexcoord;
}
