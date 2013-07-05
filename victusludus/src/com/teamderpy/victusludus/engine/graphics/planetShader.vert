//incoming Position attribute from our SpriteBatch
attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_regionuv1;
attribute vec2 a_regionuv2;
attribute vec4 a_overlayuv;
attribute vec4 a_regionsize;
attribute vec2 a_texCoord0;

//the transformation matrix of our SpriteBatch
uniform mat4 u_projTrans;

varying vec4 vColor;
varying vec2 vTexCoord;
varying vec2 vRegionUV1;
varying vec2 vRegionUV2;
varying vec4 vRegionSize;
varying vec4 vOverlayUV;
 
void main() {
	vColor = a_color;
	vTexCoord = a_texCoord0;
	vRegionUV1 = a_regionuv1;
	vRegionUV2 = a_regionuv2;
	vRegionSize = a_regionsize;
	vOverlayUV = a_overlayuv;
	
	//transform our 2D screen space position into 3D world space
	gl_Position = u_projTrans * a_position;
}