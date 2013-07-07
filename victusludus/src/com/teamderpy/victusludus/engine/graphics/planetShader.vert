//incoming Position attribute from our SpriteBatch
attribute vec4 a_position;
attribute vec4 a_color;
attribute vec4 a_overlayuv;
attribute vec4 a_textureuv;
attribute vec4 a_regionsize;
attribute vec2 a_texCoord0;
attribute vec4 a_uvoffset;

//the transformation matrix of our SpriteBatch
uniform mat4 u_projTrans;

varying vec4 vColor;
varying vec2 vTexCoord;
varying vec4 vRegionSize;
varying vec4 vOverlayUV;
varying vec4 vTextureUV;
varying vec4 vUVOffset;
 
void main() {
	vColor = a_color;
	vTexCoord = a_texCoord0;
	vRegionSize = a_regionsize;
	vOverlayUV = a_overlayuv;
	vTextureUV = a_textureuv;
	vUVOffset = a_uvoffset;
	
	//transform our 2D screen space position into 3D world space
	gl_Position = u_projTrans * a_position;
}