package com.github.polyrocketmatt.peak.math

import com.github.polyrocketmatt.game.Vec3f
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

class VectorMath {

    companion object {

        /*
        fun normal(x: Int, y: Int, size: Int, height: FloatArray, sediment: FloatArray, evaluator: NoiseEvaluator, scale: Float): Vec3f {
            val x00 = try { height[x + size + y] + sediment[x + 1][y] } catch (ex: Exception) { evaluator.noise(x + 1f, y.f()) }
            val x01 = try { height[x - size + y] + sediment[x - 1][y] } catch (ex: Exception) { evaluator.noise(x - 1f, y.f()) }
            val y00 = try { height[x][y + 1] + sediment[x][y + 1]} catch (ex: Exception) { evaluator.noise(x.f(), y + 1f) }
            val y01 = try { height[x][y - 1] + sediment[x][y - 1] } catch (ex: Exception) { evaluator.noise(x.f(), y - 1f) }
            val xy = try { height[x][y] + sediment[x][y] } catch (ex: Exception) { evaluator.noise(x.f(), y.f()) }

            val a = Vec3f(0f, scale * (y00 - xy), 1f).cross(Vec3f(1f, scale * (x00 - xy), 0f))
            val b = Vec3f(0f, scale * (y01 - xy), -1f).cross(Vec3f(-1f, scale * (x01 - xy), 0f))
            val c = Vec3f(1f, scale * (x00 - xy), 0f).cross(Vec3f(0f, scale * (y00 - xy), -1f))
            val d = Vec3f(-1f, scale * (x01 - xy), 0f).cross(Vec3f(0f, scale * (y01 - xy), 1f))

            return (a + b + c + d).normalized()
        }

         */

    }

}