package com.auth0.jwtdecodejava.impl;

import com.auth0.jwtdecodejava.algorithms.Algorithm;
import com.auth0.jwtdecodejava.algorithms.HSAlgorithm;
import com.auth0.jwtdecodejava.algorithms.NoneAlgorithm;
import com.auth0.jwtdecodejava.algorithms.RSAlgorithm;
import com.auth0.jwtdecodejava.interfaces.Header;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

import static com.auth0.jwtdecodejava.impl.ClaimImpl.extractClaim;
import static com.auth0.jwtdecodejava.impl.PublicClaims.*;

/**
 * The HeaderImpl class implements the Header interface.
 */
class HeaderImpl implements Header {
    private final Map<String, JsonNode> tree;

    HeaderImpl(Map<String, JsonNode> tree) {
        this.tree = tree;
    }

    @Override
    public Algorithm getAlgorithm() {
        String alg = extractClaim(ALGORITHM, tree).asString();
        return parseFrom(alg);
    }

    @Override
    public String getType() {
        return extractClaim(TYPE, tree).asString();
    }

    @Override
    public String getContentType() {
        return extractClaim(CONTENT_TYPE, tree).asString();
    }


    private Algorithm parseFrom(String algorithmName) {
        Algorithm algorithm = RSAlgorithm.resolveFrom(algorithmName);
        if (algorithm == null) {
            algorithm = HSAlgorithm.resolveFrom(algorithmName);
        }
        if (algorithm == null) {
            algorithm = NoneAlgorithm.resolveFrom(algorithmName);
        }
        return algorithm;
    }
}