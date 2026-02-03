#!/bin/bash

BASE_URL="http://localhost:8080"

echo "1. Creating Cliente..."
RESPONSE=$(curl -s -X POST "$BASE_URL/clientes" \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Juan Perez", "email": "juan@example.com"}')
echo "Response: $RESPONSE"
CLIENTE_ID=$(echo $RESPONSE | grep -o '"id":[0-9]*' | head -n 1 | cut -d: -f2)
echo "Cliente ID: $CLIENTE_ID"

if [ -z "$CLIENTE_ID" ]; then
  echo "Failed to create client"
  exit 1
fi

echo "2. Adding Cliente Detalles..."
curl -s -X POST "$BASE_URL/clientes/$CLIENTE_ID/detalles" \
  -H "Content-Type: application/json" \
  -d '{"telefono": "123456789", "notasInternas": "VIP Customer"}'
echo ""

echo "3. Getting Cliente (should include details)..."
curl -s -X GET "$BASE_URL/clientes/$CLIENTE_ID" 
echo ""

echo "4. Creating Pedido..."
RESPONSE_PEDIDO=$(curl -s -X POST "$BASE_URL/pedidos/cliente/$CLIENTE_ID" \
  -H "Content-Type: application/json" \
  -d '{"estado": "PENDING"}')
echo "Response: $RESPONSE_PEDIDO"
PEDIDO_ID=$(echo $RESPONSE_PEDIDO | grep -o '"id":[0-9]*' | head -n 1 | cut -d: -f2)
echo "Pedido ID: $PEDIDO_ID"

if [ -z "$PEDIDO_ID" ]; then
    echo "Failed to create pedido"
fi

echo "5. Getting Pedido..."
curl -s -X GET "$BASE_URL/pedidos/$PEDIDO_ID"
echo ""
