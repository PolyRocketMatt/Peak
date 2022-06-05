<div align="center">
    <img
        src="img/peak_logo_dark.png"
        width="800"
    />
</div>

# PEAK Library

PEAK stands for **Procedural Environment Algorithms for Kotlin**. It is a collection of various algorithms, noise types, 
modifiers, masks, filters, data extraction tools and more for creating and manipulating heightmaps 🗺️.

## Features

PEAK primarily focuses on the generation of heightmaps. Even though there are already plenty of libraries for this
out there, PEAK stands out because of the variety of algorithms it offers. While it offers you to, it doesn't force you 
to use old techniques. Instead, it allows to combine these together with new features, filters and operators to create
realistic looking terrains.

## Quickstart

Here is the table of content which describes all features of PEAK:

- [**Noise Providers & Evaluators**](#noise-providers--evaluators)
- [**Primitives**](#primitives)
- [**Simulation**](#simulation)
- [**Operators**](#operators)
- [**Filters**](#filters)
- [**Masks**](#masks)
- [**Data Extractors**](#data-extractors)
- [**3D Engine**](#3d-engine)

More detailed information on how to use PEAK can be found on the [WIKI](https://github.com/PolyRocketMatt/Peak/wiki).

---

### Noise Providers & Evaluators

Peak has a variety of noise providers and evaluators. Providers wrap certain evaluators for ease of use, but
both can be used to sample noise. There are five categories of noise offered: simple, pattern-based, complex, cellular
and bounded noise.

**1. Simple Noise Types**

Simple noise types that many libraries implement as well.

   - White noise
   - Value noise
   - Simplex noise
   - Multi-fractal noise (can be based on value, perlin or simplex noise)

Examples:

|                 Fractal                 |                 Smooth                 |                 Simplex                 |                    Value                     |                 White                 |
|:---------------------------------------:|:--------------------------------------:|:---------------------------------------:|:--------------------------------------------:|:-------------------------------------:|
| <img src="img/fractal.png" width="150"> | <img src="img/smooth.png" width="150"> | <img src="img/simplex.png" width="150"> | <img src="img/value_scaled.png" width="150"> | <img src="img/white.png" width="150"> |

(*Note: scale has been modified to produce better examples*)

**2. Pattern-based Noise Types**

Pattern-based noises create repeated patterns. These are useful for texturing or filtering.

  - Checkerboard noise
  - Grid noise
  - Radial-stripe noise
  - Straight-stripe noise

**3. Complex Noise Types**

Complex noise types combine other noise types to create interesting noise patterns.

  - Buya Noise (inspired by [Buya Noise (Cinema4D)](https://developers.maxon.net/docs/Cinema4DPythonSDK/html/types/noise.html#buya))

**4. Cellular Noise Types**

Cellular noise creates a cell-like structured noise. 

  - Cellular noise (also known as Voronoi)
  - Voronoise ([Inigo Quilez](https://iquilezles.org/articles/voronoise/))
  - Poisson noise (uses poisson samples as sample points, instead of a static sample array)

**5. Bounded Noise Types**

Bounded noise improves or amortizes certain noise types, but has the disadvantage of being bounded by
a single size parameter (see [WIKI](https://github.com/PolyRocketMatt/Peak/wiki) for more detail)

  - Perlin noise 
  - Polynomial noise (fast multi-fractal noise)

Examples:

|                    Perlin                     |                 Polynomial                 |
|:---------------------------------------------:|:------------------------------------------:|
| <img src="img/perlin_scaled.png" width="148"> | <img src="img/polynomial.png" width="148"> |

### Primitives

### Simulation

### Operators

### Filters

### Masks

### Data Extractors

### 3D Engine

~ Soon

---

<div align="center">Made with ❤️ in Leuven</div>
