package com.spaceboyross.gundam.models;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import net.minecraft.client.renderer.BufferBuilder;

/* A port of https://github.com/V0idWa1k3r/Factory0-Resources/blob/master/src/main/java/v0id/api/f0resources/client/model/WavefrontObject.java to this mod */
public class WavefrontObject {
	
	private final List<Vertex> vertices = Lists.newArrayList();
	private BufferBuilder buffBuilder = null;
	
	public WavefrontObject() {}
	
	public WavefrontObject(InputStream is) {
		this.load(is);
	}
	
	public BufferBuilder getBufferBuilder() {
		if(this.buffBuilder == null) this.buffBuilder = new BufferBuilder(this.vertices.size());
		return this.buffBuilder;
	}
	
	public List<Vertex> getVertices() {
		return this.vertices;
	}
	
	public void bakeGeometry(List<Vector3f> faces,List<Vector3f> vertexData,List<Vector2f> uvData,List<Vector3f> normalData) {
		for(Vector3f faceData : faces) {
			int vertexIndex = (int)(faceData.getX()-1);
			int uvIndex = (int)(faceData.getY()-1);
			int normalIndex = (int)(faceData.getZ()-1);
			this.vertices.add(new Vertex(vertexData.get(vertexIndex),uvData.get(uvIndex),normalData.get(normalIndex)));
		}
	}
	
	public void load(InputStream is) {
		try {
			this.load(IOUtils.toString(is,StandardCharsets.UTF_8));
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	public void load(String s) {
		List<Vector3f> tempVertexes = Lists.newArrayList();
        List<Vector2f> tempUVs = Lists.newArrayList();
        List<Vector3f> tempNormals = Lists.newArrayList();
        List<Vector3f> tempFaces = Lists.newArrayList();
        Splitter splitter = Splitter.on(Pattern.compile("\r?\n")).omitEmptyStrings().trimResults();
        int lines = 0;
        for(String line : splitter.split(s)) {
        	lines++;
        	// Ignore comments
        	if(line.charAt(0) == '#') continue;
        	
        	// Ignore user objects - this implementation only supports 1 object in a model
        	if(line.startsWith("o ")) continue; // NOTE: possibly support this?
        	
        	// Vertex
            if(line.startsWith("v ")) {
                try {
                    String[] data = line.substring(2).split(" ");
                    tempVertexes.add(new Vector3f(Float.parseFloat(data[0]),Float.parseFloat(data[1]),Float.parseFloat(data[2])));
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            // Vertex normal
            if(line.startsWith("vn ")) {
                try {
                    String[] data = line.substring(3).split(" ");
                    tempNormals.add(new Vector3f(Float.parseFloat(data[0]),Float.parseFloat(data[1]),Float.parseFloat(data[2])));
                } catch(Exception ex) {
                	ex.printStackTrace();
                }
            }

            // UV
            if(line.startsWith("vt ")) {
                try {
                    String[] data = line.substring(3).split(" ");
                    tempUVs.add(new Vector2f(Float.parseFloat(data[0]),Float.parseFloat(data[1])));
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            // Face
            if(line.startsWith("f ")) {
                try {
                    String[] data = line.substring(2).split(" ");
                    for(String faceData : data) {
                        String[] faceVertexData = faceData.split("/");
                        if(faceVertexData[1] == null || faceVertexData[1].length() < 1) faceVertexData[1] = "1";
                        tempFaces.add(new Vector3f(Integer.parseInt(faceVertexData[0]),Integer.parseInt(faceVertexData[1]),Integer.parseInt(faceVertexData[2])));
                    }
                } catch(Exception ex) {
                	ex.printStackTrace();
                }
            }
        }
        
        this.bakeGeometry(tempFaces,tempVertexes,tempUVs,tempNormals);
        tempVertexes.clear();
        tempUVs.clear();
        tempNormals.clear();
        tempFaces.clear();
	}
	
	public void putVertices(BufferBuilder buffer) {
		for(Vertex v : this.vertices) v.putVertex(buffer);
	}
	
	public class Vertex {
		public final Vector3f position;
		public final Vector2f uvset;
		public final Vector3f normals;
		
		public Vertex(Vector3f v,Vector2f v1,Vector3f v2) {
			this.position = v;
			this.uvset = v1;
			this.normals = v2;
		}
		
		public void putVertex(BufferBuilder buffer) {
			buffer.pos(this.position.x,this.position.y,this.position.z).tex(this.uvset.x,1-this.uvset.y).normal(this.normals.x,this.normals.y,this.normals.z).endVertex();
		}
	}
}